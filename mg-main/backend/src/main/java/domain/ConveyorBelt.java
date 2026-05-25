package domain;

public class ConveyorBelt extends ConveyorBeltTOP {
    @Override
    public void processEvent(String payload) {
        System.out.println("Hello from conveyor belt!!");

        setState(MachineState.RUNNING);

        // if we have a status change of the machine, set the corresponding attribute (status, or other properties)
    }
}
