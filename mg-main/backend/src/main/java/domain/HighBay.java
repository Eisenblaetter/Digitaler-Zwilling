package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.mqtt.payload.BooleanPayload;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;

public class HighBay extends HighBayTOP {
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
                    setState(MachineState.STOPPED);
                }
            }
        }

        if (topicParts.remainingTopic.equals("output/highbayActDown")) {
            BooleanPayload downPayload = deserializeBooleanPayload(payload);

            if (downPayload != null) {
                setActDown(downPayload.value());
            }

        }

        if (topicParts.remainingTopic.equals("output/highbayActCantileverForward")) {
            BooleanPayload cantileverForwardPayload = deserializeBooleanPayload(payload);

            if (cantileverForwardPayload != null) {
                setActCantileverForward(cantileverForwardPayload.value());
            }

        }

        if (topicParts.remainingTopic.equals("output/highbayActUp")) {
            BooleanPayload upPayload = deserializeBooleanPayload(payload);

            if (upPayload != null) {
                setActUp(upPayload.value());
            }

        }

        if (topicParts.remainingTopic.equals("output/highbayActCantileverBackward")) {
            BooleanPayload cantileverBackwardPayload = deserializeBooleanPayload(payload);

            if (cantileverBackwardPayload != null) {
                setActCantileverBackward(cantileverBackwardPayload.value());
            }
        }


        // TODO: If an action log is added, we could implement a slightly more complex handling mechanism and display it there.
    }
}
