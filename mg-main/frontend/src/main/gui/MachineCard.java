package gui;


import umlp.jsweet.extension.annotation.Component;
import java.util.function.Consumer;


@Component()
public class MachineCard extends MachineCardTOP {

    @Override
    public void onModelUpdate(Machine updated) {
        updateMachineInList(updated);
        rerender();
    }

    private void updateMachineInList(Machine updated) {
        for (int i = 0; i < getMachines().size(); i++) {
            Machine current = getMachines().get(i);

            // Identifikation über Name
            if (current.getName().equals(updated.getName())) {
                getMachines().set(i, updated);
                return;
            }
        }
    }

    @Override
    public Consumer<Machine> showMachineCardPopup() {
        return machine -> {
            MachineCardPopup popup = new MachineCardPopup();
            popup.setModel(machine);
            popup.show();
        }

    //das hier ist nur zur orientierung in a way
    @Override
    public Consumer<Void> alert() {
        return $ -> {
            Window.alert("Machine selected");
        };
    }
}
