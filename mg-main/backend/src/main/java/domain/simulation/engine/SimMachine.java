package domain.simulation.engine;

import jsweet.lang.Erased;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Erased
public class SimMachine {
    public enum States implements SimMachineState {
        INIT,
        FINALIZING
    }

    private static final HashMap<String, List<SimStateTransition<SimMachine>>> processes = new HashMap<>(
            Map.of(
                    "simpleProcess", List.of(
                            new SimStateTransition<>(0, States.INIT, 1000),
                            // in actual machine, add sequence of state transitions here
                            new SimStateTransition<>(0, States.FINALIZING, 1000)
                    )
            )
    );

    private final SimConfig config;

    private SimMachineState currentState = null;

    public SimMachine(SimConfig config) {
        this.config = config;
    }

    /**
     * Executes the simulation of the machine by sequentially handling the state transitions.
     */
    public void executeSimulation() {
        initSim();

        // sequentially handle transitions of selected process
        List<SimStateTransition<SimMachine>> process = processes.get(config.process);
        for (SimStateTransition<SimMachine> transition : process) {
            int extraFactorsDelay = determineExtraTimeDelay();

            try {
                Thread.sleep((long) transition.baseExecutionTimeMs * config.speedModifier + extraFactorsDelay);

                if (transition.transitionAction != null) {
                    transition.transitionAction.accept(this);
                }
                currentState = transition.toState;
            } catch (InterruptedException e) {
                // TODO: is this enough?
                Thread.currentThread().interrupt();
            }
        }

        finalizeSim();
    }

    private int determineExtraTimeDelay() {
        // TODO: based on overheating and other factors, implement on per-machine basis
        return 0;
    }

    private void initSim() {
        // wenn config.overrides gesetzt, nehme config.process und überschreibe die zustände (identifiziere nach order)

        // results objekt von montigem entweder erstellen falls noch nicht existiert oder holen und bereitstellen als
        // instanzvariable
    }

    private void finalizeSim() {
        // ändere bestimmte dinge an config je nach prozess, z.B. von sorting line zum ersten gripper füge
        // override ein für richtung des arms (links drehen statt rechts)

        // config.inputMaterial = ändere lastStage von package auf die aktuelle Stage

        // montigem results abspeichern/updaten

        // return config; // neu vorbereitete config an nächste maschine zurückgeben, wahrscheinlich inhalt außer bei package unverändert; Kopie erstellen!
    }
}
