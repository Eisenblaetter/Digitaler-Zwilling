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
    private Map<String, List<String>> machines;

    public String getBaseTopic() {
        return baseTopic;
    }

    public void setBaseTopic(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public Map<String, List<String>> getMachines() {
        return machines;
    }

    public void setMachines(Map<String, List<String>> machines) {
        this.machines = machines;
    }
}

