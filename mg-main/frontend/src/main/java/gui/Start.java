package gui;

import domain.GlobalStats;
import domain.Machine;
import domain.MachineState;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Start extends StartTOP {

    private static final int MAX_MACHINES_PREVIEW = 3;

    public GlobalStats getGlobalStats(List<GlobalStats> globalStatsList) {
        if (globalStatsList == null || globalStatsList.isEmpty()) return null;
        return globalStatsList.get(0);
    }

    public String getHealthScoreText(List<GlobalStats> globalStatsList) {
        GlobalStats stats = getGlobalStats(globalStatsList);
        if (stats == null) return "N/A";
        return stats.getHealthScore() + "%";
    }

    public String getHealthScoreColor(List<GlobalStats> globalStatsList) {
        GlobalStats stats = getGlobalStats(globalStatsList);
        if (stats == null) return "#636363";

        int score = stats.getHealthScore();
        if (score >= 80) return "#4caf50";
        if (score >= 50) return "#fbc02d";
        return "#f44336";
    }

    public String getCo2Text(List<GlobalStats> globalStatsList) {
        GlobalStats stats = getGlobalStats(globalStatsList);
        if (stats == null || stats.getTotalCo2Emissions() == null) return "N/A";
        return formatDecimal(stats.getTotalCo2Emissions()) + " g";
    }

    public String getElectricityText(List<GlobalStats> globalStatsList) {
        GlobalStats stats = getGlobalStats(globalStatsList);
        if (stats == null || stats.getTotalEnergyConsumption() == null) return "N/A";
        return formatDecimal(stats.getTotalEnergyConsumption()) + " kWh";
    }

    private String formatDecimal(double value) {
        double rounded = Math.round(value * 100.0) / 100.0;
        return String.valueOf(rounded);
    }

    public List<Machine> getTopMachines(List<Machine> machines) {
        List<Machine> topMachines = new ArrayList<>();
        if (machines == null) return topMachines;

        for (int i = 0; i < machines.size() && i < MAX_MACHINES_PREVIEW; i++) {
            topMachines.add(machines.get(i));
        }
        return topMachines;
    }

    public Boolean hasMoreMachines(List<Machine> machines) {
        return machines != null && machines.size() > MAX_MACHINES_PREVIEW;
    }

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
