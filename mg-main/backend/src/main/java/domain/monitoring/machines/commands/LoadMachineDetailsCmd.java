package domain.monitoring.machines.commands;

import de.montigem.be.command.rte.general.Command;
import domain.monitoring.machines.Machine;
import domain.monitoring.machines.MachineDetails;
import domain.monitoring.machines.MachineRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public class LoadMachineDetailsCmd extends Command<MachineDetails> {

    private String machineTopic;

    public LoadMachineDetailsCmd(String machineTopic) {
        this.machineTopic = machineTopic;
    }

    @Autowired
    MachineRegistry registry;

    @Override
    public MachineDetails execute() {
        Machine m = registry.getByTopic(machineTopic);
        if (m == null) return null;
        return MachineDetails.from(m);
    }
}
