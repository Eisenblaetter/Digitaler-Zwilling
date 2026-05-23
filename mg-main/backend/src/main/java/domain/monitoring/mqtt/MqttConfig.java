package domain.monitoring.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Value("${mqtt.topics}")
    private String topics;

    @Value("${mqtt.reconnect.enabled:true}")
    private boolean reconnectEnabled;

    @Value("${mqtt.reconnect.interval:5000}")
    private int reconnectInterval;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public String getTopics() {
        return topics;
    }

    public boolean isReconnectEnabled() {
        return reconnectEnabled;
    }

    public int getReconnectInterval() {
        return reconnectInterval;
    }
}
