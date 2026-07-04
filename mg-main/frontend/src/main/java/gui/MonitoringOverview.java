package gui;

import domain.Machine;

import java.util.function.Consumer;

public class MonitoringOverview extends MonitoringOverviewTOP {
    public Consumer<Void> showMachineCardPopup(Machine m) {
        return x -> {
            this.setMachineCardPopupVisible(true);
            this.setPopupMachineId(m.getGemId());
        };
    }

    public Consumer<Void> closeMachineCardPopup() {
        return x -> {
            this.setMachineCardPopupVisible(false);
            this.setPopupMachineId(null);
        };
    }

    public Consumer<Void> toggleView() {
        return x -> {
            this.setRowView(!this.isRowView());
        };
    }
}
