package domain.monitoring.mqtt.payload;

public record MachineFeedbackPayload(
        MachineFeedbackValue value,
        String timestamp
) {
}
