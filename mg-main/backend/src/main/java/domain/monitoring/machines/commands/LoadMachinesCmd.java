package domain.monitoring.machines.commands;

import de.montigem.be.command.rte.general.Command;
import domain.monitoring.machines.Machine;
import domain.monitoring.machines.MachineRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LoadMachinesCmd extends Command<List<Machine>> {

    @Autowired
    MachineRegistry registry;

    @Override
    public List<Machine> execute() {
        return new ArrayList<>(registry.getMachines().values());
    }
}
