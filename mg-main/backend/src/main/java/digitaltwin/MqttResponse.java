package digitaltwin;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import umlp.jsweet.extension.annotation.BackendOnly;
import umlp.jsweet.extension.annotation.BackendOnlyModes;

import java.util.UUID;


public class MqttResponse implements MqttCallback {
    IMqttClient client;

    @BackendOnly(BackendOnlyModes.STATUS_OK)
    public MqttResponse() {
        String publisherId = UUID.randomUUID().toString();
        try {
            client = new MqttClient(
                    "tcp://127.0.0.1:1883", publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            client.connect(options);

            client.subscribe("PLC/Island 1/HighBay/#");
            client.setCallback(this);
        } catch (
                MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @BackendOnly(BackendOnlyModes.STATUS_OK)
    public void connectionLost(Throwable throwable) {

    }

    @Override
    @BackendOnly(BackendOnlyModes.STATUS_OK)
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(mqttMessage);
        System.out.println(s);
        System.out.println("test");
    }

    @Override
    @BackendOnly(BackendOnlyModes.STATUS_OK)
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
