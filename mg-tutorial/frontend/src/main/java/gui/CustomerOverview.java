package gui;

import carrental.CarrentalManager;
import carrental.CarrentalObserver;
import carrental.Customer;
import carrental.EmptyCarrentalObserver;
import mc.fenix.charts.gempiecharttypes.GemPieChartData;
import mc.fenix.charts.gempiecharttypes.GemPieChartEntryBuilder;
import umlp.jsweet.extension.annotation.Component;

import java.util.List;

@Component
public class CustomerOverview extends CustomerOverviewTOP {
  CarrentalObserver obs;

  @Override
  public void init() {
    super.init();

    obs = new EmptyCarrentalObserver() {
      @Override
      public void notifyCustomerAdded(Customer customer) {
        updateAgeDistribution();
      }

      @Override
      public void notifyCustomerDeleted(Long gemId) {
        updateAgeDistribution();
      }
    };

    CarrentalManager.addObserver(obs);
    updateAgeDistribution();
  }

  @Override
  public void destroy() {
    super.destroy();
    CarrentalManager.removeObserver(obs);
  }

  public void updateAgeDistribution() {
    List<Customer> customers = getCustomers();
    if (customers == null) {
      setChartData(null);
      return;
    }

    var below25 = 0;
    var other = 0;
    var over60 = 0;
    for (var customer : customers) {
      var age = customer.getAge();
      if (age <= 25) below25++;
      else if (age < 60) other++;
      else over60++;
    }

    var data = new GemPieChartData();
    data.addEntries(new GemPieChartEntryBuilder().value(below25).label("25 and below").build().orElseThrow());
    data.addEntries(new GemPieChartEntryBuilder().value(other).label("other").build().orElseThrow());
    data.addEntries(new GemPieChartEntryBuilder().value(over60).label("60 and older").build().orElseThrow());

    setChartData(data);
  }
}
