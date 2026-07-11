package domain;

import domain.simulation.engine.CommonState;
import domain.simulation.engine.MachineState;
import domain.simulation.engine.Package;
import domain.simulation.engine.SimStateTransition;
import jsweet.lang.Erased;

import java.util.List;
import java.util.Map;

public class SortingLineSim extends SortingLineSimTOP {

    @Erased
    public enum States implements MachineState {
        DETECT_COLOR,
        SORT_ITEM
    }

    public static final String PROCESS_SORT_ITEM = "sortItem";

    @Erased
    private static final Map<String, List<SimStateTransition<SortingLineSim>>> PROCESSES = Map.of(
            PROCESS_SORT_ITEM, List.of(
                    new SimStateTransition<>(1, CommonState.INIT, 500),
                    new SimStateTransition<>(2, States.DETECT_COLOR, 1000),
                    new SimStateTransition<>(3, States.SORT_ITEM, 2000),
                    new SimStateTransition<>(4, CommonState.FINALIZING, 500)
            )
    );

    public SortingLineSim() {
        // required by the generated builder
    }

    public SortingLineSim(SimConfig config) {
        setConfig(config);
    }

    @Override
    @Erased
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Map<String, List<SimStateTransition<SimMachine>>> processes() {
        return (Map) PROCESSES;
    }

    /**
     * Example of a per-package effect: red chips need the sorting tool switched out.
     */
    @Override
    @Erased
    protected int getExtraTimeMs() {
        if (getCurrentPackage() != null && getCurrentPackage().getColor() == Package.Color.RED) {
            return 500;
        }
        return 0;
    }
}
