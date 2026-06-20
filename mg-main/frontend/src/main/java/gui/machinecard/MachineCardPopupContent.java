package gui.machinecard;

import domain.ConveyorBelt;
import domain.HighBay;
import domain.MultiProcessing;
import domain.SortingLine;

public class MachineCardPopupContent extends MachineCardPopupContentTOP {
    public Boolean isConveyorBelt() {
        return getM() instanceof ConveyorBelt;
    }

    public ConveyorBelt getConveyorBelt() {
        return (ConveyorBelt) getM();
    }

    public Boolean isMultiProcessing() {
        return getM() instanceof MultiProcessing;
    }

    public MultiProcessing getMultiProcessing() {
        return (MultiProcessing) getM();
    }

    public Boolean isSortingLine() {
        return getM() instanceof SortingLine;
    }

    public SortingLine getSortingLine() {
        return (SortingLine) getM();
    }

    public Boolean isHighBay() {
        return getM() instanceof HighBay;
    }

    public HighBay getHighBay() {
        return (HighBay) getM();
    }
}
