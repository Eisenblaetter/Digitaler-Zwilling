package domain.monitoring.machines;

import domain.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "machines-config")
@PropertySource(value = "classpath:machines.yml", factory = YamlPropertySourceFactory.class)
public class MachineConfig {
    private String baseTopic;
    private Map<String, MachineDefinition> machines;

    public String getBaseTopic() {
        return baseTopic;
    }

    public void setBaseTopic(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public Map<String, MachineDefinition> getMachines() {
        return machines;
    }

    public void setMachines(Map<String, MachineDefinition> machines) {
        this.machines = machines;
    }

    public static class MachineDefinition {
        private List<String> instances;
        private double co2PerEvent;
        private double electricityKwhPerEvent;

        public List<String> getInstances() {
            return instances;
        }

        public void setInstances(List<String> instances) {
            this.instances = instances;
        }

        public double getCo2PerEvent() {
            return co2PerEvent;
        }

        public void setCo2PerEvent(double co2PerEvent) {
            this.co2PerEvent = co2PerEvent;
        }

        public double getElectricityKwhPerEvent() {
            return electricityKwhPerEvent;
        }

        public void setElectricityKwhPerEvent(double electricityKwhPerEvent) {
            this.electricityKwhPerEvent = electricityKwhPerEvent;
        }
    }
}

