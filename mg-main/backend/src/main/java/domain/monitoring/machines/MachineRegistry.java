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
        machineConfig.getMachines().forEach((type, definition) -> {
            for (int i = 0; i < definition.getInstances().size(); i++) {
                String topic = definition.getInstances().get(i);
                String fullTopic = baseTopic + topic;
                String displayName = determineDisplayName(type, topic, i, definition.getInstances().size());
                registerMachine(type, displayName, fullTopic, definition.getCo2PerEvent(), definition.getElectricityKwhPerEvent());
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
    private void registerMachine(String type, String displayName, String fullTopic, double co2PerEvent, double electricityKwhPerEvent) {
        if (machines.containsKey(fullTopic)) {
            logger.warn("Machine with topic {} already exists, skipping creation.", fullTopic);
            return;
        }

        Optional<? extends Machine> machineOpt = createMachine(type, displayName, fullTopic, co2PerEvent, electricityKwhPerEvent);
        if (machineOpt.isPresent()) {
            Machine machine = machineOpt.get();
            machines.put(fullTopic, machine);
            logger.info("Registered machine: {} with topic: {}", displayName, fullTopic);
        } else {
            logger.error("Failed to create machine: {} (type: {}) with topic: {}. Check if all required attributes are set in the builder.", displayName, type, fullTopic);
        }
    }

    /**
     * Creates a new machine instance based on the provided type and topic.
     * MontiGem integration automatically saves successfully created objects to DB.
     */
    private Optional<? extends Machine> createMachine(String type, String displayName, String topic, double co2PerEvent, double electricityKwhPerEvent) {
        switch (type) {
            case "ConveyorBelt":
                return DomainManager.conveyorBeltBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.STOPPED)
                        .rpm(0)
                        .temperature(0.0)
                        .co2PerEvent(co2PerEvent)
                        .electricityKwhPerEvent(electricityKwhPerEvent)
                        .lastMaintenance("Never")
                        .maintenanceDue("Unknown")
                        .lastUpdate("Never")
                        .conveyorRunning(false)
                        .forward(false)
                        .build();
            case "HighBay":
                return DomainManager.highBayBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.STOPPED)
                        .rpm(0)
                        .temperature(0.0)
                        .co2PerEvent(co2PerEvent)
                        .electricityKwhPerEvent(electricityKwhPerEvent)
                        .lastMaintenance("Never")
                        .maintenanceDue("Unknown")
                        .lastUpdate("Never")
                        .dirty(false)
                        .ejectedToBranch("None")
                        .actDown(false)
                        .actUp(false)
                        .actCantileverForward(false)
                        .actCantileverBackward(false)
                        .build();
            case "MultiProcessingwithOven":
                return DomainManager.multiProcessingBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.STOPPED)
                        .rpm(0)
                        .temperature(0.0)
                        .co2PerEvent(co2PerEvent)
                        .electricityKwhPerEvent(electricityKwhPerEvent)
                        .lastMaintenance("Never")
                        .maintenanceDue("Unknown")
                        .lastUpdate("Never")
                        .colorPresent("None")
                        .conveyorRunning(false)
                        .dirty(false)
                        .ejectedToBranch("None")
                        .actOvenInward(false)
                        .sensOven(false)
                        .ovenLight(false)
                        .actGripperToOven(false)
                        .sensTurntablePosVacuum(false)
                        .actRotClockwise(false)
                        .sensTurntablePosSaw(false)
                        .actSaw(false)
                        .conveyorForward(false)
                        .compressor(false)
                        .build();
            case "SortingLine":
                return DomainManager.sortingLineBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.STOPPED)
                        .rpm(0)
                        .temperature(0.0)
                        .co2PerEvent(co2PerEvent)
                        .electricityKwhPerEvent(electricityKwhPerEvent)
                        .lastMaintenance("Never")
                        .maintenanceDue("Unknown")
                        .lastUpdate("Never")
                        .colorPresent("None")
                        .conveyorRunning(false)
                        .compressorRunning(false)
                        .actRedEjector(false)
                        .dirty(false)
                        .ejectedToBranch("None")
                        .build();
            case "VacuumGripper":
                return DomainManager.vacuumGripperBuilder()
                        .name(type)
                        .displayName(displayName)
                        .mqttTopic(topic)
                        .state(MachineState.STOPPED)
                        .rpm(0)
                        .temperature(0.0)
                        .co2PerEvent(co2PerEvent)
                        .electricityKwhPerEvent(electricityKwhPerEvent)
                        .lastMaintenance("Never")
                        .maintenanceDue("Unknown")
                        .lastUpdate("Never")
                        .actVerticalUp(false)
                        .actVerticalDown(false)
                        .actRotRight(false)
                        .actRotLeft(false)
                        .actArmOut(false)
                        .actArmIn(false)
                        .compressorOn(false)
                        .valve(false)
                        .dirty(false)
                        .ejectedToBranch("None")
                        .build();

            default:
                logger.warn("Unknown machine type: {}", type);
                return Optional.empty();
        }
    }

    public Machine getMachineByTopic(String topic) {
        return machines.get(topic);
    }

    public Map<String, Machine> getMachines() {
        return machines;
    }
}
