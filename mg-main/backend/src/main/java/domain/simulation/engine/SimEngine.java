package domain.simulation.engine;

import domain.ConveyorBeltSim;
import domain.SimConfig;
import domain.SimMachine;
import domain.SortingLineSim;
import domain.VacuumGripperSim;
import jsweet.lang.Erased;

import java.util.ArrayList;
import java.util.List;

@Erased
public class SimEngine {

    /**
     * Runs the predefined full production process sequentially in one thread.
     * The baseConfig (speed, temperature, input materials, ...) comes from the
     * GUI; the process of each machine is fixed here.
     * <p>
     * Sequential batch: each machine processes all packages, then hands the
     * list to the next machine.
     *
     * @return the processed packages (lastStage set by each machine)
     */
    public List<Package> runFullSimpleProcess(SimConfig baseConfig) throws InterruptedException {
        List<SimMachine> machines = List.of(
                createMachine("sortingLine", baseConfig, SortingLineSim.PROCESS_SORT_ITEM),
                createMachine("vacuumGripper", baseConfig, VacuumGripperSim.PROCESS_TAKE_ITEM_OFF_SORTING_LINE),
                createMachine("conveyorBelt", baseConfig, ConveyorBeltSim.PROCESS_TRANSPORT_ITEM),
                createMachine("vacuumGripper", baseConfig, VacuumGripperSim.PROCESS_TAKE_ITEM_OFF_CONVEYOR_BELT)
                // TODO: extend with the remaining machines of the plant (high bay, multi processing, ...)
        );

        // TODO: create/update the montigem Simulation entity (RUNNING/FINISHED/FAILED) around this run
        // TODO: if we want overrides of config from one machine to another, save attr previousMachineConfig and merge
        List<Package> packages = createPackages(baseConfig);
        for (SimMachine machine : machines) {
            packages = machine.executeSimulation(packages);
        }
        return packages;
    }

    /**
     * Runs individually selected machines, each independently in its own virtual thread
     */
    public List<Thread> runIndividualMachines(List<SimMachine> machines) {
        // TODO: change param here to machine names, wire with createMachine

        List<Thread> threads = new ArrayList<>();
        for (SimMachine machine : machines) {
            threads.add(Thread.ofVirtual()
                    .name("sim-" + machine.getClass().getSimpleName())
                    .start(() -> {
                        try {
                            // each machine processes the materials declared in its own config
                            machine.executeSimulation(createPackages(machine.getConfig()));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }));
        }
        return threads;
    }

    public SimMachine createMachine(String machineName, SimConfig config, String processName) {
        SimConfig machineConfig = configFor(config, processName);
        return switch (machineName) {
            case "sortingLine" -> {
                SortingLineSim sl = new SortingLineSim();
                sl.setConfig(machineConfig);
                yield sl;
            }
            case "vacuumGripper" -> {
                VacuumGripperSim vg = new VacuumGripperSim();
                vg.setConfig(machineConfig);
                yield vg;
            }
            case "conveyorBelt" -> {
                ConveyorBeltSim cb = new ConveyorBeltSim();
                cb.setConfig(machineConfig);
                yield cb;
            }
            default -> throw new IllegalArgumentException("Unknown machine: " + machineName);
        };
    }

    /**
     * Copy of the base config with the machine's process selected (each machine gets its own copy).
     */
    private static SimConfig configFor(SimConfig baseConfig, String processName) {
        SimConfig config = baseConfig.copy();
        config.setProcessName(processName);
        return config;
    }

    /**
     * Creates the runtime packages from the config's inputMaterials declaration.
     */
    private static List<Package> createPackages(SimConfig config) {
        List<Package.Color> colors = config.getInputMaterials();
        List<Package> packages = new ArrayList<>();
        if (colors != null) {
            for (int i = 0; i < colors.size(); i++) {
                packages.add(new Package(i + 1, colors.get(i)));
            }
        }
        return packages;
    }
}
