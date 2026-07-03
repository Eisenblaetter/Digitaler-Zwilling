package gui.simulationcard;

import domain.SimulationState;
import umlp.jsweet.extension.annotation.Component;

@Component
public class SimulationCard extends SimulationCardTOP {

    @Override
    public String getStatusColor(SimulationState state) {
        if (state == null) return "#6f6f6f";
        switch (state) {
            case RUNNING:  return "#0043ce"; // IBM Carbon Blue 70
            case FINISHED: return "#198038"; // IBM Carbon Green 60
            case FAILED:   return "#da1e28"; // IBM Carbon Red 60
            default:       return "#6f6f6f"; // IBM Carbon Gray 50
        }
    }

    @Override
    public String getStatusLabel(SimulationState state) {
        if (state == null) return "Unknown";
        switch (state) {
            case RUNNING:  return "Running";
            case FINISHED: return "Finished";
            case FAILED:   return "Failed";
            default:       return "Unknown";
        }
    }
}
