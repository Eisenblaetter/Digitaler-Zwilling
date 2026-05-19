/*SOL start GUI Functions*/
package gui;

import carrental.Car;
import carrental.RentableStatus;
import carrental.commands.RentCar;
import umlp.backendrte.command.CommandManager;
import umlp.backendrte.command.CommandResult;
import umlp.backendrte.command.StatusResult;
import umlp.guiutil.Router;
import umlp.jsweet.extension.annotation.Component;

import java.util.function.Consumer;

import static umlp.backendrte.command.CommandManager.executeCommand;

@Component()
public class CarOverview extends CarOverviewTOP {
  
  // TODO: Adding functions in GUI
  @Override
  public String getAvailableCarsTitle() {
    if (this.getCars() == null) {
      return "";
    }
    int i = 0;
    for (Car c : this.getCars()) {
      if (c.getRentableStatus() == RentableStatus.AVAILABLE) {
        i++;
      }
    }
    return i + " available cars";
  }
  
  @Override
  public Consumer<Void> rentCar(Car car) {
    return x -> {
      CommandManager.executeCommand(new RentCar(car.getGemId()));
    };
  }
  
  // TODO: Updating Objects - Navigation
  @Override
  public Consumer<Void> updateCar(Car car) {
    return $ -> {
      Router.navigate("gui", "CarUpdate", String.valueOf(car.getGemId()));
    };
  }
}
/*SOL end GUI Functions*/