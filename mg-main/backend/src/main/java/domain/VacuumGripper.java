package domain;

import domain.monitoring.machines.MachineTopicParts;
import domain.monitoring.machines.TopicMessageType;
import domain.monitoring.mqtt.payload.BooleanPayload;

import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import static domain.monitoring.mqtt.payload.PayloadDeserializer.deserializeBooleanPayload;

public class VacuumGripper extends VacuumGripperTOP  {

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

        if (topicParts.remainingTopic.equals("/output/vacuumActVerticalUp")){
            BooleanPayload verticalUpPayload = deserializeBooleanPayload(payload);

            if (verticalUpPayload != null){
                setActVerticalUp(verticalUpPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActVerticalDown")){
            BooleanPayload verticalDownPayload = deserializeBooleanPayload(payload);

            if (verticalDownPayload != null){
                setActVerticalDown(verticalDownPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActRotRight")){
            BooleanPayload rotRightPayload = deserializeBooleanPayload(payload);

            if (rotRightPayload != null){
                setActRotRight(rotRightPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActRotLeft")){
            BooleanPayload rotLeftPayload = deserializeBooleanPayload(payload);

            if (rotLeftPayload != null){
                setActRotLeft(rotLeftPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActArmOut")){
            BooleanPayload armOutPayload = deserializeBooleanPayload(payload);

            if (armOutPayload != null){
                setActArmOut(armOutPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActArmIn")){
            BooleanPayload armInPayload = deserializeBooleanPayload(payload);

            if (armInPayload != null){
                setActArmIn(armInPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActCompressorOn")){
            BooleanPayload compressorOnPayload = deserializeBooleanPayload(payload);

            if (compressorOnPayload != null){
                setCompressorOn(compressorOnPayload.value());
            }
        }

        if (topicParts.remainingTopic.equals("/output/vacuumActValve")){
            BooleanPayload valvePayload = deserializeBooleanPayload(payload);

            if (valvePayload != null){
                setValve(valvePayload.value());
            }
        }

    }
}
