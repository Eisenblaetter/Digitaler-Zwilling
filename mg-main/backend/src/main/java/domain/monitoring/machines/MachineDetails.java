package domain.monitoring.machines;

import domain.Machine;
import domain.MachineState;
import java.util.ArrayList;
import java.util.List;


public class MachineDetails {

    public String name;
    public String displayName;
    public MachineState state;
    public Double temperature;
    public Double load;
    public Double throughput;
    public Double energyConsumption;
    public String maintenanceDue;
    public List<String> errors;
    public String imageName;
    public String lastUpdate;

    public static MachineDetails from(Machine m) {
        MachineDetails d = new MachineDetails();
        d.name = m.getName();
        d.displayName = m.getDisplayName();
        d.state = m.getState();
        d.temperature = m.getTemperature();
        d.load = m.getLoad();
        d.throughput = m.getThroughput();
        d.energyConsumption = m.getEnergyConsumption();
        d.maintenanceDue = m.getMaintenanceDue();
        d.errors = new ArrayList<>();
        for (int i = 0; i < m.sizeErrors(); i++) {
            d.errors.add(m.getErrors(i));
        }
        d.lastUpdate = m.getLastUpdate();
        return d;
    }
}
