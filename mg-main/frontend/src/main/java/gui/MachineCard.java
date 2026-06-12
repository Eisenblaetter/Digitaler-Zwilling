package gui;

import domain.DomainManager;
import domain.Machine;
import domain.MachineState;

import java.util.List;
import java.util.function.Consumer;

@umlp.jsweet.extension.annotation.Component
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

    public Consumer<Void> showMachineCardPopup(Machine m) {
        return x -> {
            this.setShowDialog(true);
            Long gemId = Long.valueOf(m.getGemId());
            this.setCurrentlySelectedMachine(gemId);
        };
    }

    @Override
    public Consumer<Void> closeDialog() {
        return x -> {
            this.setShowDialog(false);
            this.setCurrentlySelectedMachine(null);
        };
    }

    public Machine getMachine() {
        List<Machine> machines = this.getMachines();
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i).getGemId() == this.getCurrentlySelectedMachine()) {
                return machines.get(i);
            }
        }
        return null;
    }

    public String getCurrentMachineDisplayName() {
        Machine m = this.getMachine();
        return m == null ? "" : m.getDisplayName();
    }
}
