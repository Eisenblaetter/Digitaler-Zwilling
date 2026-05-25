package domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

public class Machine extends MachineTOP {

    // TODO: refactor this mess

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public void processEvent(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        Logger logger = LoggerFactory.getLogger(Machine.class);
        try {
            JsonNode root = mapper.readTree(payload);
            JsonNode valueNode = root.get("value");

            if (valueNode != null) {
                if (valueNode.isTextual() && valueNode.asText().contains("MACHINE_FEEDBACK")) {
                    handleMachineFeedback(valueNode.asText());
                    // TODO: also handle feedback from machine/.../isExecuting, set status to RUNNING if true
                } else {
                    handleValue(valueNode);
                    // TODO: pass more than just payload, also what sensor etc.
                }
            }
        } catch (Exception e) {
            logger.error("Error parsing MQTT payload: {}", payload, e);
        }
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    protected void handleMachineFeedback(String feedbackJson) {
        ObjectMapper mapper = new ObjectMapper();
        Logger logger = LoggerFactory.getLogger(Machine.class);
        try {
            JsonNode feedback = mapper.readTree(feedbackJson);
            String status = feedback.get("status").asText();
            logger.info("Setting machine {} state to {}", getName(), status);
            setState(MachineState.valueOf(status));
        } catch (Exception e) {
            logger.error("Error parsing machine feedback: {}", feedbackJson, e);
        }
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    protected void handleValue(Object value) {
        JsonNode node = (JsonNode) value;
        Logger logger = LoggerFactory.getLogger(Machine.class);
        if (node.isNumber()) {
            logger.info("Machine {} received numerical sensor value: {}", getName(), node.asDouble());
        } else if (node.isBoolean()) {
            logger.info("Machine {} received boolean sensor value: {}", getName(), node.asBoolean());
        } else {
            logger.debug("Received value for {}: {}", getName(), node);
        }
    }
}
