package domain.monitoring.mqtt;

import domain.ConveyorBelt;
import domain.GlobalStats;
import domain.Machine;
import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.stats.GlobalStatsRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import domain.monitoring.machines.MachineRegistry;

@Component
public class MqttMessageRouter {

    private static final Logger logger = LoggerFactory.getLogger(MqttMessageRouter.class);

    private final MachineRegistry machineRegistry;
    private final GlobalStatsRegistry globalStatsRegistry;

    @Autowired
    public MqttMessageRouter(MachineRegistry machineRegistry, GlobalStatsRegistry globalStatsRegistry) {
        this.machineRegistry = machineRegistry;
        this.globalStatsRegistry = globalStatsRegistry;
    }

    public void routeMessage(String topic, String payload) {
        MachineTopicParts topicParts = new MachineTopicParts(topic);
        if (topicParts.isStandaloneEvent) return; // ignore standalone events for now

        logger.debug("Routing message for machine: {}", topicParts.machineIdentifier);

        Machine machineObj = machineRegistry.getMachineByTopic(topicParts.getIdentifyingTopic());
        if (machineObj == null) {
            logger.warn("No machine registered for topic: {}", topicParts.getIdentifyingTopic());
            return;
        }

        GlobalStats globalStats = globalStatsRegistry.getInstance();
        machineObj.processMessage(topicParts, payload, globalStats);

        // TODO: see if we need to add calls to some action log here
    }
}
