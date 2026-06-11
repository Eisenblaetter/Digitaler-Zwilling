package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.mqtt.payload.BooleanPayload;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;

public class ConveyorBelt extends ConveyorBeltTOP {

    @Override
    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMeasurement(MachineTopicParts topicParts, String payload) {

        if (topicParts == null || payload == null) {
            return;
        }

        super.processMeasurement(topicParts, payload);

        if (topicParts.remainingTopic.equals("internal/isExecuting")) {
            BooleanPayload executingPayload = deserializeBooleanPayload(payload);

            if (executingPayload != null) {
                if (!executingPayload.value()) {
                    setConveyorRunning(false);
                }
            }
        }

        if (topicParts.remainingTopic.equals("output/conveyorActForward")) {
            BooleanPayload forwardPayload = deserializeBooleanPayload(payload);

            if (forwardPayload != null) {
                setForward(forwardPayload.value());
                if(forwardPayload.value()){
                    setConveyorRunning(true);
                }
            }

        }

        if (topicParts.remainingTopic.equals("output/conveyorActBackward")) {
            BooleanPayload backwardPayload = deserializeBooleanPayload(payload);

            if (backwardPayload != null) {
                if(backwardPayload.value()){
                    setConveyorRunning(true);
                }
            }

        }
    }
}
