package domain.simulation.engine;

import jsweet.lang.Erased;

import java.util.function.Consumer;

@Erased
public class SimStateTransition<T extends SimMachine> {
    int order;
    SimMachineState toState;
    int baseExecutionTimeMs;
    Consumer<T> transitionAction;

    public SimStateTransition(int order, SimMachine.States toState, int baseExecutionTimeMs) {
        this(order, toState, baseExecutionTimeMs, null);
    }

    public SimStateTransition(int order, SimMachine.States toState, int baseExecutionTimeMs, Consumer<T> transitionAction) {
        this.order = order;
        this.toState = toState;
        this.baseExecutionTimeMs = baseExecutionTimeMs;
        this.transitionAction = transitionAction;
    }
}
