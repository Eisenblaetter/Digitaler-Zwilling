package gui;

import umlp.guiutil.HTMLUtils;
import umlp.jsweet.extension.annotation.Component;

import java.util.function.Consumer;

@Component
public class MachineDetailsPopup extends MachineDetailsPopupTOP {

    // Alle Parameter aus der GUI
    private String name;
    private String status;
    private Integer temperature;
    private Integer rpm;
    private Integer load;
    private String lastMaintenance;


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public void setRpm(Integer rpm) {
        this.rpm = rpm;
    }

    @Override
    public void setLoad(Integer load) {
        this.load = load;
    }

    @Override
    public void setLastMaintenance(String lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    @Override
    public Consumer<Void> closePopup() {
        return $ -> {
            HTMLUtils.consoleLog("Closing popup for machine: " + name);
            HTMLUtils.closePopup();
        };
    }

    @Override
    public Consumer<Void> debugInfo() {
        return $ -> {
            HTMLUtils.alert(
                    "Machine: " + name +
                            "\nStatus: " + status +
                            "\nTemp: " + temperature +
                            "\nRPM: " + rpm +
                            "\nLoad: " + load +
                            "\nLast Maintenance: " + lastMaintenance
            );
        };
    }
}
