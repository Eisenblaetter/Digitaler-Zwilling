package domain.monitoring.mqtt.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

public class PayloadDeserializer {
    /**
     * Sanitizes malformed timestamps that contain both a UTC offset and a trailing 'Z',
     * e.g. "+00:00Z" → "+00:00".
     */
    private static String sanitizeTimestamp(String payload) {
        return payload.replaceAll("(\\+\\d{2}:\\d{2})Z\"", "$1\"");
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public static IntegerPayload deserializeIntegerPayload(String payload) {
        Logger logger = LoggerFactory.getLogger(PayloadDeserializer.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(sanitizeTimestamp(payload), IntegerPayload.class);
        } catch (Exception e) {
            logger.error("Failed to deserialize integer payload: {}", payload, e);
            return null;
        }
    }

    @BackendOnly(BackendOnlyModes.THROW_ERROR)
    public static BooleanPayload deserializeBooleanPayload(String payload) {
        Logger logger = LoggerFactory.getLogger(PayloadDeserializer.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(sanitizeTimestamp(payload), BooleanPayload.class);
        } catch (Exception e) {
            logger.error("Failed to deserialize boolean payload: {}", payload, e);
            return null;
        }
    }

    // TODO: also add string payload for events
}
