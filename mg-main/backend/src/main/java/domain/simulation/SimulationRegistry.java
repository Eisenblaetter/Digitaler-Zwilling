package domain.simulation;

import domain.DomainManager;
import domain.Simulation;
import domain.SimulationState;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimulationRegistry {

    private final Map<Integer, Simulation> simulations = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        initializeSimulations();
    }

    public void initializeSimulations() {
        List<Simulation> existing = DomainManager.getSimulationList();
        if (existing != null) {
            existing.forEach(s -> simulations.put(s.getSimulationId(), s));
        }


        if (simulations.isEmpty()) {
            registerSimulation(1, "Full Production Run – Batch 42",   SimulationState.RUNNING,  37,  820, 137, 5);
            registerSimulation(2, "Baseline Throughput – Q2 2025",    SimulationState.FINISHED, 180, 4320, 144, 6);
            registerSimulation(3, "High-Speed Stress Test",            SimulationState.FINISHED, 60,  1980, 165, 4);
            registerSimulation(4, "Reduced-Machine Efficiency Check",  SimulationState.FAILED,   22,  310,  78, 2);
            registerSimulation(5, "Night-Shift Minimal Load",          SimulationState.FINISHED, 240, 5040, 140, 6);
        }
    }


    private void registerSimulation(int id, String name, SimulationState state, int duration, int total, int avg, int count) {
        Optional<? extends Simulation> simulationOpt = createSimulation(id, name, state, duration, total, avg, count);
        if (simulationOpt.isPresent()) {
            Simulation s = simulationOpt.get();
            simulations.put(id, s);
        }
    }


    private Optional<? extends Simulation> createSimulation(int id, String name, SimulationState state, int duration, int total, int avg, int count) {
        return DomainManager.simulationBuilder()
                .simulationId(id)
                .name(name)
                .state(state)
                .duration(duration)
                .totalConsumption(total)
                .avgConsumption(avg)
                .countMachines(count)
                .build();
    }

    public List<Simulation> getAllSimulations() {
        return new ArrayList<>(simulations.values());
    }

    public Simulation getSimulationById(int id) {
        return simulations.get(id);
    }


}
