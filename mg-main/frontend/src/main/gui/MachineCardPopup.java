package gui;

import umlp.jsweet.extension.annotation.Component;
import java.util.function.Consumer;

@Component
public class MachineCardPopup extends MachineCardPopupTOP {

    @Override
    public Consumer<Void> closePopup() {
        return $ -> {
            hide();
        };

    @Override
    public void onModelUpdate(Machine m) {
        setModel(m);
        rerender();
    }
    }
}
