package domain.monitoring.mqtt;

import domain.ConveyorBelt;
import domain.Machine;
import domain.monitoring.machines.MachineTopicParts;
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
        MachineTopicParts topicParts = new MachineTopicParts(topic);
        if (topicParts.isStandaloneEvent) return; // ignore standalone events for now

        logger.debug("Routing message for machine: {}", topicParts.machineIdentifier);

        Machine machineObj = machineRegistry.getMachineByTopic(topic);
        if (machineObj == null) {
            logger.warn("No machine registered for topic: {}", topic);
            return;
        }
        machineObj.processMessage(topicParts, payload);

        // TODO: see if we need to add calls to some action log here
    }
}
