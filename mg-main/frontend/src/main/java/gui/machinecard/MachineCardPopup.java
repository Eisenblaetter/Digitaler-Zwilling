package gui.machinecard;

import domain.DomainManager;
import domain.Machine;

public class MachineCardPopup extends MachineCardPopupTOP {
    public Machine loadMachine(long gemId) {
        // TODO: close popup if gemId is null or some error happens here
        // TODO: see if we can pass machine directly through events to prevent double loading
        return DomainManager.getMachineSure(gemId);
    }

    public String getCurrentMachineDisplayName() {
        Machine m = this.loadMachine(this.getMachineGemId());
        return m == null ? "" : m.getDisplayName();
    }
}
