/*SOL start GUI Update objects*/
package gui;

import carrental.Car;
import carrental.CarrentalManager;
import carrental.RentableStatus;
import umlp.guiutil.Router;

import java.util.function.Consumer;

public class CarCreate extends CarCreateTOP {
  
  @Override
  public Consumer<Void> createAndNavigate(Car car) {
    return $ -> {
      CarrentalManager.carBuilder()
          .make(car.getMake())
          .model(car.getModel())
          .costPerDay(car.getCostPerDay())
          .mileage(0.0f)
          .name(car.getName())
          .rentableStatus(RentableStatus.AVAILABLE)
          .build();
      Router.navigate("gui", "CarOverview");
    };
  }
}
/*SOL end GUI Update objects*/