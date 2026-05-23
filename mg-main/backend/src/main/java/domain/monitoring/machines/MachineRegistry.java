package domain.monitoring.machines;

import domain.ConveyorBelt;
import domain.DomainManager;
import domain.Machine;
import domain.MachineState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class MachineRegistry {

    private static final Logger logger = LoggerFactory.getLogger(MachineRegistry.class);

    private final Map<String, Machine> machines = new ConcurrentHashMap<>();

    public MachineRegistry() {
        initializeMachines();
    }

    public void initializeMachines() {
        Machine cv = DomainManager.conveyorBeltBuilder()
                .mqttTopic("")
                .state(MachineState.UNINITIALIZED_IDLE)
                .build()
                .get(); // TODO: check if exists

        machines.put("PLC/Island 1/ConveyorBelt/ConveyorBelt01", cv);

        // TODO: extrapolate this for all available machines, read from config file
    }

    public void processMachineEvent(String machineType, String topic, String payload) {
        logger.debug("Processed event for {}: {}", machineType, payload);

        // TODO: add generic log in MachineEventLogs or some class
        // we can then filter this for individual machine cards to show an action log, and theoretically also display a complete action log for entire monitoring

        // note: save everything inside an action log together with the corresponding machine id,
        // do parsing of the payloads inside the extended individual machines
    }

    public Machine getMachineByTopic(String topic) {
        // TODO: improve error handling and make more robust
        String[] topicParts = topic.split("/");
        if (topicParts.length > 4) {
            topicParts = Arrays.copyOfRange(topicParts, 0, 4);
        }
        String shortTopic = String.join("/", topicParts);

        return machines.get(shortTopic);
    }
}
