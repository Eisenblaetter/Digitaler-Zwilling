package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.machines.TopicMessageType;
import domain.monitoring.mqtt.payload.BooleanPayload;
import domain.monitoring.mqtt.payload.PayloadDeserializer;
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

        // do general handling like special status setting here
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMeasurement(MachineTopicParts topicParts, String payload) {
        // check for internal/isExecuting to set running status, etc.
        if (topicParts.remainingTopic.equals("internal/isExecuting")) {
            // execution status is always json with boolean value, deserialize as such
            BooleanPayload isExecuting = PayloadDeserializer.deserializeBooleanPayload(payload);
            if (isExecuting == null) return;

            if (isExecuting.value()) {
                setState(MachineState.RUNNING);
            } else {
                setState(MachineState.INITIALIZED_IDLE); // set if we should actually set it to this or stopped
            }
        }

        // TODO: see if we have other common things to handle here, otherwise handle everything else in sub-machines
    }
}
