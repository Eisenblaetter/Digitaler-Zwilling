package domain.monitoring.mqtt.payload;

public record BooleanPayload(boolean value, String timestamp) {
    public Boolean getValue() {
        return value;
    }
}
