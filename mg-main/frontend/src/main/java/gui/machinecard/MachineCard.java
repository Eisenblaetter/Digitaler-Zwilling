package gui.machinecard;

import domain.DomainManager;
import domain.Machine;
import domain.MachineState;
import umlp.jsweet.extension.annotation.Component;

import java.util.function.Consumer;

@Component
public class MachineCard extends MachineCardTOP {
    @Override
    public String getStatusTextColor(MachineState state) {
        switch (state) {
            case RUNNING:
                return "#4caf50";
            case STOPPED:
                return "#f44336";
            default:
                return "#636363";
        }
    }
}
