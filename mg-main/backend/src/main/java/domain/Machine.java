package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.machines.TopicMessageType;
import domain.monitoring.mqtt.payload.BooleanPayload;
import domain.monitoring.mqtt.payload.MachineFeedbackPayload;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;
import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeMachineFeedbackPayload;

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
        if (topicParts.remainingTopic.equals("emitted/machine_feedback")) {
            MachineFeedbackPayload feedbackPayload = deserializeMachineFeedbackPayload(payload);
            if (feedbackPayload != null) {
                try {
                    MachineState newState = MachineState.valueOf(feedbackPayload.value().status());
                    if (newState == MachineState.INITIALIZED_ACTIVE
                            || newState == MachineState.UNINITIALIZED_ACTIVE) return;

                    setState(newState);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        // see if we need to handle more special events/commands for all machines here
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMeasurement(MachineTopicParts topicParts, String payload) {
        if (topicParts.remainingTopic.equals("internal/isExecuting")) {
            // execution status is always json with boolean value, deserialize as such
            BooleanPayload isExecuting = deserializeBooleanPayload(payload);
            if (isExecuting == null) return;

            if (isExecuting.value()) {
                setState(MachineState.RUNNING);
            }
        }

        // TODO: see if we have other common things to handle here, otherwise handle everything else in sub-machines
    }
}
