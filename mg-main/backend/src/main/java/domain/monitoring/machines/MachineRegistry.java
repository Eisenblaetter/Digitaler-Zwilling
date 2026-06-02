package domain.monitoring.machines;

import domain.DomainManager;
import domain.Machine;
import domain.MachineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

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
    }

    @PostConstruct
    public void init() {
        initializeMachines();
    }

    public void initializeMachines() {
        // Load existing machines from DB to avoid duplicates
        DomainManager.getMachineList().forEach(m -> {
            if (m.getMqttTopic() != null) {
                machines.put(m.getMqttTopic(), m);
            }
        });

        if (machineConfig.getMachines() == null) {
            logger.warn("No machines configured in MachineConfig");
            return;
        }

        String baseTopic = machineConfig.getBaseTopic();

        // TODO: see if we can simplify saving logic to map
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
        if (machines.containsKey(fullTopic)) {
            logger.debug("Machine with topic {} already exists, skipping creation.", fullTopic);
            return;
        }
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
        switch (type) {
            case "ConveyorBelt":
                return DomainManager.conveyorBeltBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.UNINITIALIZED_IDLE)
                        .build();
            case "HighBay":
                return DomainManager.highBayBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.UNINITIALIZED_IDLE)
                        .build();
            case "MultiProcessingwithOven":
                return DomainManager.multiProcessingBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.UNINITIALIZED_IDLE)
                        .build();
            case "SortingLine":
                return DomainManager.sortingLineBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.UNINITIALIZED_IDLE)
                        .build();
            case "VacuumGripper":
                return DomainManager.vacuumGripperBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.UNINITIALIZED_IDLE)
                        .build();
            default:
                logger.warn("Unknown machine type: {}", type);
                return Optional.empty();
        }
    }

    public Machine getMachineByTopic(String topic) {
        return machines.entrySet().stream()
                .filter(entry -> topic.startsWith(entry.getKey()))
                .max(java.util.Comparator.comparingInt(entry -> entry.getKey().length()))
                .map(Map.Entry::getValue)
                .orElse(null);
    }
}
