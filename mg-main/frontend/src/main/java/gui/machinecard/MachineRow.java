package gui.machinecard;

import domain.MachineState;
import umlp.jsweet.extension.annotation.Component;

@Component
public class MachineRow extends MachineCardTOP {
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
