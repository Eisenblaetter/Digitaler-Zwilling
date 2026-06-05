package domain.monitoring.machines.commands;

import domain.monitoring.machines.MachineRegistry;
import domain.monitoring.machines.MachineDetails;

public class LoadMachineDetailsCmd {

    private final String machineTopic;
    private final MachineRegistry registry;

    public LoadMachineDetailsCmd(String machineTopic, MachineRegistry registry) {
        this.machineTopic = machineTopic;
        this.registry = registry;
    }

    public MachineDetails execute() {
        return MachineDetails.from(registry.getMachineByTopic(machineTopic));
    }
}
