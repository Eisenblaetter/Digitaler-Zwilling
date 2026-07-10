package gui;

import domain.Simulation;
import domain.Machine;
import java.util.function.Consumer;

public class SimulationOverview extends SimulationOverviewTOP {

    public Consumer<Void> showSimulationCardPopup(Simulation s) {
        return x -> {
            this.setSimulationCardPopupVisible(true);
            this.setPopupSimulationId(s.getGemId());
        };
    }

    public Consumer<Void> closeSimulationCardPopup() {
        return x -> {
            this.setSimulationCardPopupVisible(false);
            this.setPopupSimulationId(null);
        };
    }

    public Consumer<Void> openNewSimulationConfig() {
        return x -> {
            this.setNewSimulationPopupVisible(true);
            this.setNewSimulationName("");
            this.setNewSimulationSpeed(1);
            // Reset machine selection
            if (this.getMachines() != null) {
                for (Machine m : this.getMachines()) {
                    m.setSelected(false);
                }
            }
        };
    }

    public Consumer<Void> closeNewSimulationPopup() {
        return x -> {
            this.setNewSimulationPopupVisible(false);
        };
    }

    public Consumer<Void> startSimulation() {
        return x -> {
            // Logic to start simulation would go here
            // For now, just close the popup
            this.setNewSimulationPopupVisible(false);
            
            // Log for debugging (transpiled to console.log)
            System.out.println("Starting simulation: " + this.getNewSimulationName());
        };
    }
}
