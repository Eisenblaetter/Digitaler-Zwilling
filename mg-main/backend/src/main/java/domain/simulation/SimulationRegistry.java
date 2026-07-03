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
            registerSimulation(1, "Simulation abc", SimulationState.RUNNING, 45, 350, 350, 1);
            registerSimulation(2, "Simulation xxx", SimulationState.FINISHED, 120, 1500, 750, 2);
            registerSimulation(3, "Simulation xyz", SimulationState.FAILED, 100, 100, 70, 3);
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
