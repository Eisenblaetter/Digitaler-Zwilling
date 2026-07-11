package domain;

import domain.simulation.engine.CommonState;
import domain.simulation.engine.Package;
import domain.simulation.engine.SimStateTransition;
import jsweet.lang.Erased;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SimMachine extends SimMachineTOP {

    public SimMachine() {
    }

    public SimMachine(SimConfig config) {
        setConfig(config);
    }

    @Erased
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** The package currently being processed; available to transition actions and getExtraTimeMs(). */
    @Erased
    private Package currentPackage;

    @Erased
    public Package getCurrentPackage() {
        return currentPackage;
    }

    @Erased
    protected Map<String, List<SimStateTransition<SimMachine>>> processes() {
        return Map.of();
    }

    /**
     * Extra delay per machine based on overheating and other factors; may
     * inspect getCurrentPackage() for per-package effects (e.g. color).
     */
    @Erased
    protected int getExtraTimeMs() {
        return 0;
    }

    /**
     * Runs the selected process once, without input material.
     */
    @Erased
    public void executeSimulation() throws InterruptedException {
        executeSimulation(List.of());
    }

    /**
     * Executes the simulation of the machine: the selected process runs once
     * per input package (sequential batch) or once if there are none. The
     * packages are returned for handover to the next machine.
     */
    @Erased
    public List<Package> executeSimulation(List<Package> inputMaterials) throws InterruptedException {
        List<SimStateTransition<SimMachine>> process = initSim();

        try {
            if (inputMaterials.isEmpty()) {
                runProcess(process);
            }
            for (Package pkg : inputMaterials) {
                currentPackage = pkg;
                runProcess(process);
                pkg.setLastStage(getClass().getSimpleName());
            }
        } catch (InterruptedException | RuntimeException e) {
            setCurrentState(CommonState.ERROR.toString());
            throw e;
        } finally {
            currentPackage = null;
        }

        finishSim();
        return inputMaterials;
    }

    /**
     * Sequentially handles the state transitions of the process
     */
    @Erased
    private void runProcess(List<SimStateTransition<SimMachine>> process) throws InterruptedException {
        for (SimStateTransition<SimMachine> transition : process) {
            Thread.sleep((long) (transition.baseExecutionTimeMs() / getConfig().getSpeedModifier())
                    + getExtraTimeMs());

            if (transition.transitionAction() != null) {
                transition.transitionAction().accept(this);
            }
            setCurrentState(transition.toState().toString());
        }
    }

    @Erased
    private List<SimStateTransition<SimMachine>> initSim() {
        setCurrentState(CommonState.INIT.toString());

        if (getConfig().getSpeedModifier() == null || getConfig().getSpeedModifier() <= 0) {
            throw new IllegalArgumentException("speedModifier must be > 0");
        }

        String processName = getConfig().getProcessName();
        List<SimStateTransition<SimMachine>> process = processes().get(processName);
        if (process == null) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " has no process named '" + processName + "', available: " + processes().keySet());
        }

        logger.debug("initSim: processName={}, process={}", processName, process);

        return applyOverrides(process);
    }

    /**
     * Merges the transitionOverrides from the config into the process (upsert):
     * an override replaces the step with the same order or is inserted as a new step.
     */
    @Erased
    private List<SimStateTransition<SimMachine>> applyOverrides(List<SimStateTransition<SimMachine>> process) {
        List<SimStateTransition<SimMachine>> overrides = getConfig().getTransitionOverrides();
        if (overrides == null || overrides.isEmpty()) {
            return process;
        }

        Map<Integer, SimStateTransition<SimMachine>> byOrder = new TreeMap<>();
        process.forEach(step -> byOrder.put(step.order(), step));
        overrides.forEach(override -> byOrder.put(override.order(), override));

        logger.debug("applyOverrides: overrides={}, result={}", overrides, byOrder.values());
        return List.copyOf(byOrder.values());
    }

    @Erased
    protected void finishSim() {
        // TODO: save/update montigem simulation results
    }
}
