package domain.monitoring.mqtt;

import domain.ConveyorBelt;
import domain.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import domain.monitoring.machines.MachineRegistry;

@Component
public class MqttMessageRouter {

    private static final Logger logger = LoggerFactory.getLogger(MqttMessageRouter.class);

    private final MachineRegistry machineRegistry;

    @Autowired
    public MqttMessageRouter(MachineRegistry machineRegistry) {
        this.machineRegistry = machineRegistry;
    }

    public void routeMessage(String topic, String payload) {
        String[] parts = topic.split("/");
        if (parts.length >= 3) {
            String machineType = parts[2]; // e.g. "HighBay"

            // give specific event handling to individual machine objects
            logger.debug("Routing message for machine type: {}", machineType);

            Machine machineObj = machineRegistry.getMachineByTopic(topic);
            if (machineObj == null) {
                logger.warn("No machine registered for topic: {}", topic);
                return;
            }

            switch (machineType) { // TODO: adjust for all machine types
                case "ConveyorBelt" -> {
                    if (machineObj instanceof ConveyorBelt) {
                        ((ConveyorBelt) machineObj).processEvent();
                    }
                }
                case "HighBay" -> {

                }
                case "MultiProcessingwithOven" -> {

                }
                case "SortingLine" -> {

                }
                case "VacuumGripper" -> {

                }
                case "PunchingMachine" -> {

                }
                default -> {

                }
            }

            // add generic log for all actions
            machineRegistry.processMachineEvent(machineType, topic, payload);
        } else {
            logger.warn("Unrecognized topic format: {}", topic);
        }
    }
}
