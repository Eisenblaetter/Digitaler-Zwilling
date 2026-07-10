package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.mqtt.payload.BooleanPayload;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;

public class MultiProcessing extends MultiProcessingTOP{

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
                setConveyorRunning(executingPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingActOvenInward")) {
            BooleanPayload inwardPayload = deserializeBooleanPayload(payload);

            if (inwardPayload != null) {
                setActOvenInward(inwardPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("input/multiProcessingSensOven")) {
            BooleanPayload sensOvenPayload = deserializeBooleanPayload(payload);

            if (sensOvenPayload != null) {
                setSensOven(sensOvenPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingOvenLight")) {
            BooleanPayload ovenLightPayload = deserializeBooleanPayload(payload);

            if (ovenLightPayload != null) {
                setOvenLight(ovenLightPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingActGripperToOven")) {
            BooleanPayload gripperPayload = deserializeBooleanPayload(payload);

            if (gripperPayload != null) {
                setActGripperToOven(gripperPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("input/multiProcessingSensTurntablePosVacuum")) {
            BooleanPayload vacuumPayload = deserializeBooleanPayload(payload);

            if (vacuumPayload != null) {
                setSensTurntablePosVacuum(vacuumPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingActRotClockwise")) {
            BooleanPayload rotClockwisePayload = deserializeBooleanPayload(payload);

            if (rotClockwisePayload != null) {
                setActRotClockwise(rotClockwisePayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("input/multiProcessingSensTurntablePosSaw")) {
            BooleanPayload sawPayload = deserializeBooleanPayload(payload);

            if (sawPayload != null) {
                setSensTurntablePosSaw(sawPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingActSaw")) {
            BooleanPayload sawPayload = deserializeBooleanPayload(payload);

            if (sawPayload != null) {
                setActSaw(sawPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingConveyorForward")) {
            BooleanPayload conveyorForwardPayload = deserializeBooleanPayload(payload);

            if (conveyorForwardPayload != null) {
                setConveyorForward(conveyorForwardPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("output/multiProcessingCopressor")) {
            BooleanPayload compressorPayload = deserializeBooleanPayload(payload);

            if (compressorPayload != null) {
                setCompressor(compressorPayload.value());
            }
        }



    }
}
