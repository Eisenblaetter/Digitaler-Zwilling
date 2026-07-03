package gui.navigation;

import umlp.guiutil.Router;
import umlp.jsweet.extension.annotation.Component;

import java.util.function.Consumer;

@Component
public class IconButton extends IconButtonTOP {

    @Override
    public Consumer<Void> handleClick() {
        if (this.getRouterLink() != null && !this.getRouterLink().isEmpty()) {
            return navToRouterLink();
        }
        if (this.getOnClick() != null) {
            return this.getOnClick();
        }
        return x -> {
        };
    }

    private Consumer<Void> navToRouterLink() {
        return x -> {
            Router.navigate(this.getRouterLink());
        };
    }
}
