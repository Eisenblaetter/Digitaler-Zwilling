package domain.monitoring.machines;

import java.util.ArrayList;
import java.util.List;

public class MachineDetails {

    public String name;
    public String displayName;
    public String mqttTopic;
    public String state;
    public double temperature;
    public double load;
    public double throughput;
    public double energyConsumption;
    public boolean maintenanceDue;
    public List<String> errors = new ArrayList<>();
    public String imageName;
    public long lastUpdate;

    public static MachineDetails from(MachineTOP m) {
        MachineDetails d = new MachineDetails();

        d.name = m.getName();
        d.displayName = m.getDisplayName();
        d.mqttTopic = m.getMqttTopic();
        d.state = m.getState().name();
        d.temperature = m.getTemperature();
        d.load = m.getLoad();
        d.throughput = m.getThroughput();
        d.energyConsumption = m.getEnergyConsumption();
        d.maintenanceDue = m.getMaintenanceDue();
        d.imageName = m.getImageName();
        d.lastUpdate = m.getLastUpdate();

        // Fehlerliste korrekt aus MachineTOP extrahieren
        for (int i = 0; i < m.sizeErrors(); i++) {
            d.errors.add(m.getErrors(i));
        }

        return d;
    }
}
