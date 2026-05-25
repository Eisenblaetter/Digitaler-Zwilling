package domain.monitoring.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Service
public class MqttClientService implements MqttCallbackExtended {

    private static final Logger logger = LoggerFactory.getLogger(MqttClientService.class);

    private final MqttConfig mqttConfig;
    private final MqttMessageRouter messageRouter;
    private IMqttClient client;

    @Autowired
    public MqttClientService(MqttConfig mqttConfig, MqttMessageRouter messageRouter) {
        this.mqttConfig = mqttConfig;
        this.messageRouter = messageRouter;
    }

    @PostConstruct
    public void init() {
        new Thread(this::connectWithRetry).start();
    }

    private void connectWithRetry() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (client == null) {
                    client = new MqttClient(mqttConfig.getBrokerUrl(), mqttConfig.getClientId(), new MemoryPersistence());
                    client.setCallback(this);
                }

                if (client.isConnected()) {
                    break;
                }

                logger.info("Connecting to MQTT broker at {}", mqttConfig.getBrokerUrl());

                MqttConnectOptions options = new MqttConnectOptions();
                options.setAutomaticReconnect(mqttConfig.isReconnectEnabled());
                options.setCleanSession(true);
                options.setConnectionTimeout(30);

                client.connect(options);
                break;
            } catch (MqttException e) {
                logger.error("Error connecting to MQTT broker: {}. Retrying in {}ms...", e.getMessage(), mqttConfig.getReconnectInterval());
            }

            try {
                Thread.sleep(mqttConfig.getReconnectInterval());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
                logger.info("Disconnected MQTT client");
            }
        } catch (MqttException e) {
            logger.error("Error during MQTT client cleanup", e);
        }
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        logger.info("Connected to MQTT broker: {}. Reconnect: {}", serverURI, reconnect);
        try {
            client.subscribe(mqttConfig.getTopics());
            logger.info("Subscribed to topics: {}", mqttConfig.getTopics());
        } catch (MqttException e) {
            logger.error("Error subscribing to topics after connection", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        logger.warn("MQTT connection lost", cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        logger.debug("Received message on topic {}: {}", topic, payload);
        messageRouter.routeMessage(topic, payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Not used as we mainly subscribe
    }
}
