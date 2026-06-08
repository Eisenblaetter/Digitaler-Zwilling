package domain;

public class ConveyorBelt extends ConveyorBeltTOP {
    public void processEvent() {
        System.out.println("Hello from conveyor belt!!");

        setState(MachineState.RUNNING);

        // if we have a status change of the machine, set the corresponding attribute (status, or other properties)
    }
}
