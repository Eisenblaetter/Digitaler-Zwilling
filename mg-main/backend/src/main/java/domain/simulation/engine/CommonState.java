package domain.simulation.engine;

import jsweet.lang.Erased;

/**
 * States shared by all simulated machines
 */
@Erased
public enum CommonState implements MachineState {
    INIT,
    ERROR,
    FINALIZING
}
