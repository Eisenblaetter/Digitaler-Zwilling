package domain;

import domain.simulation.engine.CommonState;
import domain.simulation.engine.MachineState;
import domain.simulation.engine.SimStateTransition;
import jsweet.lang.Erased;

import java.util.List;
import java.util.Map;

public class ConveyorBeltSim extends ConveyorBeltSimTOP {

    @Erased
    public enum States implements MachineState {
        TRANSPORT_ITEM
    }

    public static final String PROCESS_TRANSPORT_ITEM = "transportItem";
    // TODO: depending on real system, split into transport (forward -> backward belt movement) and transportReverse (backward -> forward)

    @Erased
    private static final Map<String, List<SimStateTransition<ConveyorBeltSim>>> PROCESSES = Map.of(
            PROCESS_TRANSPORT_ITEM, List.of(
                    new SimStateTransition<>(1, CommonState.INIT, 500),
                    new SimStateTransition<>(2, States.TRANSPORT_ITEM, 3000),
                    new SimStateTransition<>(3, CommonState.FINALIZING, 500)
            )
    );

    public ConveyorBeltSim() {
        // required by the generated builder
    }

    public ConveyorBeltSim(SimConfig config) {
        setConfig(config);
    }

    @Override
    @Erased
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Map<String, List<SimStateTransition<SimMachine>>> processes() {
        return (Map) PROCESSES;
    }
}
