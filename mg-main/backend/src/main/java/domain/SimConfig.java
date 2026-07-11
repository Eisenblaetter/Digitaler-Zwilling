package domain;

import domain.simulation.engine.Package;
import domain.simulation.engine.SimStateTransition;
import jsweet.lang.Erased;

import java.util.ArrayList;
import java.util.List;

public class SimConfig extends SimConfigTOP {

    @Erased
    private List<SimStateTransition<SimMachine>> transitionOverrides;

    @Erased
    public List<SimStateTransition<SimMachine>> getTransitionOverrides() {
        return transitionOverrides;
    }

    @Erased
    public void setTransitionOverrides(List<SimStateTransition<SimMachine>> transitionOverrides) {
        this.transitionOverrides = transitionOverrides;
    }

    // declaration of the chips entering the plant (in order); the engine
    // creates the runtime Package objects from this at the start of a run
    @Erased
    private List<Package.Color> inputMaterials;

    @Erased
    public List<Package.Color> getInputMaterials() {
        return inputMaterials;
    }

    @Erased
    public void setInputMaterials(List<Package.Color> inputMaterials) {
        this.inputMaterials = inputMaterials;
    }

    // create copy for handover to next machine (references the erased overrides -> backend only)
    @Erased
    public SimConfig copy() {
        SimConfig copy = new SimConfig();
        if (getSpeedModifier() != null) {
            copy.setSpeedModifier(getSpeedModifier());
        }
        if (getProcessName() != null) {
            copy.setProcessName(getProcessName());
        }
        if (getTemperatureOverride() != null) {
            copy.setTemperatureOverride(getTemperatureOverride());
        }
        if (transitionOverrides != null) {
            copy.setTransitionOverrides(new ArrayList<>(transitionOverrides));
        }
        if (inputMaterials != null) {
            copy.setInputMaterials(new ArrayList<>(inputMaterials));
        }
        return copy;
    }
}
