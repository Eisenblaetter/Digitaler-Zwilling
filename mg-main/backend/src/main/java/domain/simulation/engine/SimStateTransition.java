package domain.simulation.engine;

import domain.SimMachine;
import jsweet.lang.Erased;

import java.util.function.Consumer;

@Erased
public record SimStateTransition<T extends SimMachine>(int order, MachineState toState, int baseExecutionTimeMs,
                                                       Consumer<T> transitionAction) {

    public SimStateTransition(int order, MachineState toState, int baseExecutionTimeMs) {
        this(order, toState, baseExecutionTimeMs, null);
    }
}
