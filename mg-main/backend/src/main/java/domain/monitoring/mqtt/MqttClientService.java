package domain.monitoring.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MqttClientService implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(MqttClientService.class);

    private final MqttConfig mqttConfig;
    private final MqttMessageRouter messageRouter;

    @Autowired
    public MqttClientService(MqttConfig mqttConfig, MqttMessageRouter messageRouter) {
        this.mqttConfig = mqttConfig;
        this.messageRouter = messageRouter;
    }

    @PostConstruct
    public void init() {
        connect();
    }

    private void connect() {
        try (IMqttClient client = new MqttClient(mqttConfig.getBrokerUrl(), mqttConfig.getClientId())) {
            logger.info("Connecting to MQTT broker at {}", mqttConfig.getBrokerUrl());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(mqttConfig.isReconnectEnabled());
            options.setCleanSession(true);
            options.setConnectionTimeout(30);

            client.connect(options);
            logger.info("Connected successfully. Subscribing to {}", mqttConfig.getTopics());

            client.setCallback(this);
            client.subscribe(mqttConfig.getTopics());
        } catch (MqttException e) {
            System.out.println("err");
            // TODO: add better error handling here
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
