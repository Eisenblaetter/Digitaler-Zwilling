package domain.monitoring.commands;

import domain.Machine;
import domain.monitoring.machines.MachineRegistry;

import java.util.List;

//Für MachineCard
public class LoadMachinesCmd {

    private final MachineRegistry registry;

    public LoadMachinesCmd(MachineRegistry registry) {
        this.registry = registry;
    }

    public List<Machine> execute() {
        return registry.getMachines().values().stream().toList();
    }
}
