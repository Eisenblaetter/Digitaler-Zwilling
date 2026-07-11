package domain.simulation.engine;

import domain.SimConfig;
import domain.SimMachine;
import jsweet.lang.Erased;

import java.util.List;
import java.util.Map;

@Erased
public class SortingLineSim extends SimMachine {

    public enum States implements MachineState {
        DETECT_COLOR,
        SORT_ITEM
    }

    public static final String PROCESS_SORT_ITEM = "sortItem";

    private static final Map<String, List<SimStateTransition<SortingLineSim>>> PROCESSES = Map.of(
            PROCESS_SORT_ITEM, List.of(
                    new SimStateTransition<>(1, CommonState.INIT, 500),
                    new SimStateTransition<>(2, States.DETECT_COLOR, 1000),
                    new SimStateTransition<>(3, States.SORT_ITEM, 2000),
                    new SimStateTransition<>(4, CommonState.FINALIZING, 500)
            )
    );

    public SortingLineSim(SimConfig config) {
        super(config);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Map<String, List<SimStateTransition<SimMachine>>> processes() {
        return (Map) PROCESSES;
    }

    /** Example of a per-package effect: red chips need the sorting tool switched out. */
    @Override
    protected int getExtraTimeMs() {
        if (getCurrentPackage() != null && getCurrentPackage().getColor() == Package.Color.RED) {
            return 500;
        }
        return 0;
    }
}
