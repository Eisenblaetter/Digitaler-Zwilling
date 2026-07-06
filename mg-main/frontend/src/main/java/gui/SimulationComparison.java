package gui;

import domain.Simulation;
import domain.SimulationState;
import mc.fenix.input.gemdropdowninputtypes.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationComparison extends SimulationComparisonTOP {


    Simulation getSelectedSim(String id, List<Simulation> simulations) {
        if (simulations == null || simulations.isEmpty()) {
            System.out.println("FEHLER: Liste ist leer!");
        }
        for (Simulation s : simulations) {
            if (("" + s.getSimulationId()).equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Option> getSimulationOptions(List<Simulation> simulations) {

        System.out.println("DEBUG: Simulationsliste Größe: " + simulations.size());
        if (simulations == null || simulations.isEmpty()) {
            return new ArrayList<>();
        }

        List<Option> options = new ArrayList<>();
        for (Simulation s : simulations) {
            String id = String.valueOf(s.getSimulationId());
            String name = s.getName();

            options.add(new Option() {
                @Override
                public String getValue() { return id; }
                @Override
                public String getDisplay() { return name; }
            });
        }

        return options;
    }




    @Override
    public String getStatusColor(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getState() == null) {
            return "#636363";
        }

        switch (s.getState()) {
            case RUNNING:
                return "#2196F3";
            case FINISHED:
                return "#4CAF50";
            case FAILED:
                return "#F44336";
            default:
                return "#636363";
        }
    }


    @Override
    public String getSimulationState(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getState() == null) {
            return "";
        }
        return s.getState().toString();
    }
    @Override
    public String getSimulationDuration(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getDuration() == null) {
            return "";
        }
        return s.getDuration().toString() + " sec";
    }
    @Override
    public String getTotalConsumption(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getTotalConsumption() == null) {
            return "";
        }
        return s.getTotalConsumption().toString() + "kWh";
    }
    @Override
    public String getAvgConsumption(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getAvgConsumption() == null) {
            return "";
        }
        return s.getAvgConsumption().toString() + "kWh";
    }

    @Override
    public String getSimulatedMachinesCount(String id, List<Simulation> simulations) {
        Simulation s = getSelectedSim(id, simulations);

        if (s == null || s.getCountMachines() == null) {
            return "";
        }
        return s.getCountMachines().toString();
    }


    // Das gehört auch noch anders dann
    @Override
    public List<String> getSimulatedMachinesList(String id) {
        List<String> list = new ArrayList<>();
        if ("1".equals(id)) {
            list.add("Conveyor Belt");
        }
        if ("2".equals(id)) {
            list.add("Conveyor Belt");
            list.add("Vacuum Gripper 1");
        }
        if ("3".equals(id)) {
            list.add("Conveyor Belt");
            list.add("Vacuum Gripper 1");
            list.add("Vacuum Gripper 2");
        }
        return list;
    }


}
