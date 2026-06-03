package domain.monitoring.mqtt.payload;

public record MachineFeedbackValue(String jsonType, String status, String info) {
}
