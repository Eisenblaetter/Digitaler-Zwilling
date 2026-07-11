package domain;

import domain.simulation.engine.CommonState;
import domain.simulation.engine.MachineState;
import domain.simulation.engine.SimStateTransition;
import jsweet.lang.Erased;

import java.util.List;
import java.util.Map;

public class VacuumGripperSim extends VacuumGripperSimTOP {

    @Erased
    public enum States implements MachineState {
        GRAB_ITEM,
        ROTATE_ARM,
        RELEASE_ITEM
    }

    public static final String PROCESS_TAKE_ITEM_OFF_SORTING_LINE = "takeItemOffSortingLine";
    public static final String PROCESS_TAKE_ITEM_OFF_CONVEYOR_BELT = "takeItemOffConveyorBelt";

    @Erased
    private static final Map<String, List<SimStateTransition<VacuumGripperSim>>> PROCESSES = Map.of(
            PROCESS_TAKE_ITEM_OFF_SORTING_LINE, List.of(
                    new SimStateTransition<>(1, CommonState.INIT, 500),
                    new SimStateTransition<>(2, States.GRAB_ITEM, 1000,
                            machine -> machine.setItemGrabbed(true)),
                    new SimStateTransition<>(3, States.ROTATE_ARM, 2000),
                    new SimStateTransition<>(4, States.RELEASE_ITEM, 1000,
                            machine -> machine.setItemGrabbed(false)),
                    new SimStateTransition<>(5, CommonState.FINALIZING, 500)
            ),
            PROCESS_TAKE_ITEM_OFF_CONVEYOR_BELT, List.of(
                    new SimStateTransition<>(1, CommonState.INIT, 500),
                    new SimStateTransition<>(2, States.ROTATE_ARM, 2000),
                    new SimStateTransition<>(3, States.GRAB_ITEM, 1000,
                            machine -> machine.setItemGrabbed(true)),
                    new SimStateTransition<>(4, States.RELEASE_ITEM, 1000,
                            machine -> machine.setItemGrabbed(false)),
                    new SimStateTransition<>(5, CommonState.FINALIZING, 500)
            )
    );

    public VacuumGripperSim() {
        // required by the generated builder
    }

    public VacuumGripperSim(SimConfig config) {
        setConfig(config);
    }

    @Override
    @Erased
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Map<String, List<SimStateTransition<SimMachine>>> processes() {
        return (Map) PROCESSES;
    }
}
