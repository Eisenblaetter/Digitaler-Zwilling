package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.machines.TopicMessageType;
import domain.monitoring.mqtt.payload.BooleanPayload;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;

public class SortingLine extends SortingLineTOP {

    @Override
    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processMeasurement(MachineTopicParts topicParts, String payload) {

        if (topicParts == null || payload == null) {
            return;
        }

        super.processMeasurement(topicParts, payload);

        if (topicParts.getMessageType() == TopicMessageType.MEASUREMENT) {

            BooleanPayload boolPayload =
                    deserializeBooleanPayload(payload);

            handleBooleanMeasurement(topicParts, boolPayload);
        }
    }

    private void handleBooleanMeasurement(
            MachineTopicParts topicParts,
            BooleanPayload payload
    ) {

        if (topicParts == null || payload == null) {
            return;
        }

        if (topicParts.getMeasurement() == MachineTopicParts.MeasurementType.CONVEYOR_RUNNING) {
            setConveyorRunning(payload.getValue());
        }

        if (topicParts.getMeasurement() == MachineTopicParts.MeasurementType.COMPRESSOR_RUNNING) {
            setCompressorRunning(payload.getValue());
        }

        if (topicParts.getMeasurement() == MachineTopicParts.MeasurementType.ACT_RED_EJECTOR) {
            setActRedEjector(payload.getValue());
        }
    }
}
