/*SOL start Class Services*/
package carrental.commands;

import carrental.Car;
import carrental.CarrentalManager;
import carrental.RentableStatus;
import umlp.backendrte.command.StatusResult;

public class RentCar extends RentCarTOP {
  
  public RentCar(long rentedCarId) {
    super(rentedCarId);
  }
  
  @Override
  public StatusResult doAction() {
    Car c = CarrentalManager.getCarSure(getRentedCarGemId());
    c.setRentableStatus(RentableStatus.RENTED);
    return StatusResult.ok(getId());
  }
}
/*SOL end Class Services*/