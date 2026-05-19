/* (c) https://github.com/MontiCore/monticore */

package digitaltwin;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.UUID;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
        scanBasePackages = {"umlp.backendrte.service.websocket", "umlp.backendrte.service.rest", "digitaltwin.service"}
)
public class DigitalTwinServerApplication extends DigitalTwinServerApplicationTOP {
    public static void main(String[] args) {
        SpringApplication.run(DigitalTwinServerApplication.class, args);
    }

    @PostConstruct
    @Override
    public void init() throws IOException {
        super.init();
        initEntities();
        initMqtt();
    }

    protected void initEntities() {
        // TODO: initialize machines, etc.
    }

    protected void initMqtt() {
        MqttResponse bla = new MqttResponse();
        System.out.println("tried to make some cool thingy let's see if it works");
    }
}
