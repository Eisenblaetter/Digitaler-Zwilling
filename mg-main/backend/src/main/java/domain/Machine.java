package domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.machines.TopicMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

public class Machine extends MachineTOP {

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMessage(MachineTopicParts topicParts, String payload) {
        if (topicParts.messageType == TopicMessageType.EVENT) {
            processEvent(topicParts, payload);
        } else if (topicParts.messageType == TopicMessageType.MEASUREMENT) {
            processMeasurement(topicParts, payload);
        }
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processEvent(MachineTopicParts topicParts, String payload) {
        // deserialize nested json payload and see what info we need and can further extract

        // do general handling like status setting here
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMeasurement(MachineTopicParts topicParts, String payload) {
        // check if payload is bool or number payload, deserialize accordingly

        // check for internal/isExecuting to set running status, etc.
    }
}
