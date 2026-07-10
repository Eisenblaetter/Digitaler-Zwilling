package domain.simulation.engine;

import jsweet.lang.Erased;

import java.util.List;

@Erased
public class SimConfig {
    int speedModifier;
    // List<Package> inputMaterials;
    String process;
    List<SimStateTransition<SimMachine>> transitionOverrides;
    double temperatureOverride;

    // TODO: add more config options such as next due maintenance, wear, etc.
}
