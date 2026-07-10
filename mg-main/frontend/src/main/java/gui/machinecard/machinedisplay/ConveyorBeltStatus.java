package gui.machinecard.machinedisplay;

public class ConveyorBeltStatus extends ConveyorBeltStatusTOP {
    public String getBeltDirection() {
        String baseText = "Belt direction: ";
        return baseText + (getBelt().isForward() ? "Forward" : "Backward");
    }
}
