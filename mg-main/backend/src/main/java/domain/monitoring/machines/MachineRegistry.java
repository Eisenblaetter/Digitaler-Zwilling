package domain.monitoring.machines;

import domain.DomainManager;
import domain.Machine;
import domain.MachineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class MachineRegistry {

    private static final Logger logger = LoggerFactory.getLogger(MachineRegistry.class);
    private final MachineConfig machineConfig;

    private final Map<String, Machine> machines = new ConcurrentHashMap<>();

    @Autowired
    public MachineRegistry(MachineConfig machineConfig) {
        this.machineConfig = machineConfig;
        initializeMachines();
    }

    public void initializeMachines() {
        if (machineConfig.getMachines() == null) {
            logger.warn("No machines configured in MachineConfig");
            return;
        }

        String baseTopic = machineConfig.getBaseTopic();

        machineConfig.getMachines().forEach((name, topics) -> {
            for (int i = 0; i < topics.size(); i++) {
                String topic = topics.get(i);
                String fullTopic = baseTopic + topic;

                String displayName = (topics.size() > 1) ? name + " #" + (i + 1) : name;

                Optional<? extends Machine> machineOpt = createMachine(name, displayName, fullTopic);

                machineOpt.ifPresent(machine -> {
                    machines.put(fullTopic, machine);
                    logger.info("Registered machine: {} with topic: {}", displayName, fullTopic);
                });
            }
        });
    }

    private Optional<? extends Machine> createMachine(String type, String displayName, String topic) {
        return switch (type) {
            case "ConveyorBelt" -> DomainManager.conveyorBeltBuilder()
                    .name(type)
                    .displayName(displayName)
                    .mqttTopic(topic)
                    .state(MachineState.UNINITIALIZED_IDLE)
                    .build();
            case "HighBay" -> DomainManager.highBayBuilder()
                    .name(type)
                    .displayName(displayName)
                    .mqttTopic(topic)
                    .state(MachineState.UNINITIALIZED_IDLE)
                    .build();
            case "MultiProcessingwithOven" -> DomainManager.multiProcessingBuilder()
                    .name(type)
                    .displayName(displayName)
                    .mqttTopic(topic)
                    .state(MachineState.UNINITIALIZED_IDLE)
                    .build();
            case "SortingLine" -> DomainManager.sortingLineBuilder()
                    .name(type)
                    .displayName(displayName)
                    .mqttTopic(topic)
                    .state(MachineState.UNINITIALIZED_IDLE)
                    .build();
            case "VacuumGripper" -> DomainManager.vacuumGripperBuilder()
                    .name(type)
                    .displayName(displayName)
                    .mqttTopic(topic)
                    .state(MachineState.UNINITIALIZED_IDLE)
                    .build();
            default -> {
                logger.warn("Unknown machine type: {}", type);
                yield Optional.empty();
            }
        };
    }

    public void processMachineEvent(String machineType, String topic, String payload) {
        logger.debug("Processed event for {}: {}", machineType, payload);

        // TODO: add generic log in MachineEventLogs or some class
        // we can then filter this for individual machine cards to show an action log, and theoretically also display a complete action log for entire monitoring

        // note: save everything inside an action log together with the corresponding machine id,
        // do parsing of the payloads inside the extended individual machines
    }

    public Machine getMachineByTopic(String topic) {
        return machines.entrySet().stream()
                .filter(entry -> topic.startsWith(entry.getKey()))
                .max(java.util.Comparator.comparingInt(entry -> entry.getKey().length()))
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}
