package domain.monitoring.mqtt.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsweet.lang.Erased;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Erased
public class PayloadDeserializer {
    private static final Logger logger = LoggerFactory.getLogger(PayloadDeserializer.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Sanitizes malformed timestamps that contain both a UTC offset and a trailing 'Z',
     * e.g. "+00:00Z" → "+00:00".
     */
    private static String sanitizeTimestamp(String payload) {
        return payload.replaceAll("(\\+\\d{2}:\\d{2})Z\"", "$1\"");
    }

    public static IntegerPayload deserializeIntegerPayload(String payload) {
        try {
            return objectMapper.readValue(sanitizeTimestamp(payload), IntegerPayload.class);
        } catch (Exception e) {
            logger.error("Failed to deserialize integer payload: {}", payload, e);
            return null;
        }
    }

    public static BooleanPayload deserializeBooleanPayload(String payload) {
        try {
            return objectMapper.readValue(sanitizeTimestamp(payload), BooleanPayload.class);
        } catch (Exception e) {
            logger.error("Failed to deserialize boolean payload: {}", payload, e);
            return null;
        }
    }

    public static MachineFeedbackPayload deserializeMachineFeedbackPayload(String payload) {
        try {
            JsonNode root = objectMapper.readTree(sanitizeTimestamp(payload));
            String timestamp = root.has("timestamp") ? root.get("timestamp").asText() : null;
            MachineFeedbackValue value = null;
            if (root.has("value")) {
                JsonNode valueNode = root.get("value");
                String valueStr = valueNode.isTextual() ? valueNode.asText() : valueNode.toString();
                value = objectMapper.readValue(valueStr, MachineFeedbackValue.class);
            }
            return new MachineFeedbackPayload(value, timestamp);
        } catch (Exception e) {
            logger.error("Failed to deserialize machine feedback payload: {}", payload, e);
            return null;
        }
    }
}
