package gui.machinecard.machinedisplay;

public class MultiProcessingStatus extends MultiProcessingStatusTOP {
    public String getOvenLightStatus() {
        return getMp().isOvenLight() ? "ON" : "OFF";
    }
}
