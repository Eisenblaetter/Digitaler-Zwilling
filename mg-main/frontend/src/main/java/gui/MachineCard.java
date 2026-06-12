package gui;

import domain.MachineState;

public class MachineCard extends MachineCardTOP {
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
