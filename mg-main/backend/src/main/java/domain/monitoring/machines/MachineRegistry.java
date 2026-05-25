package domain.monitoring.machines;

import domain.DomainManager;
import domain.Machine;
import domain.MachineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        machineConfig.getMachines().forEach((type, topics) -> {
            for (int i = 0; i < topics.size(); i++) {
                String topic = topics.get(i);
                String fullTopic = baseTopic + topic;
                String displayName = determineDisplayName(type, topic, i, topics.size());
                registerMachine(type, displayName, fullTopic);
            }
        });
    }

    private String determineDisplayName(String type, String topic, int index, int totalCount) {
        String machineNumber = topic.replaceAll("^.*?(\\d+)$", "$1");
        if (machineNumber.matches("\\d+")) {
            return type + " #" + Integer.parseInt(machineNumber);
        } else if (totalCount > 1) {
            return type + " #" + (index + 1); // fallback to count if no number found in topic
        }
        return type;
    }

    /**
     * Registers a new machine instance with the (in-memory) registry.
     */
    private void registerMachine(String type, String displayName, String fullTopic) {
        createMachine(type, displayName, fullTopic).ifPresent(machine -> {
            machines.put(fullTopic, machine);
            logger.info("Registered machine: {} with topic: {}", displayName, fullTopic);
        });
    }

    /**
     * Creates a new machine instance based on the provided type and topic.
     * MontiGem integration automatically saves successfully created objects to DB.
     */
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
        logger.debug("Logged event for {}: {} to monitoring action log", machineType, payload);

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
