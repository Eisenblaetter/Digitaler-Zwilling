package gui;

import carrental.CarrentalManager;
import carrental.Customer;
import umlp.jsweet.extension.annotation.Component;

import java.util.function.Consumer;

@Component
public class CustomerCreate extends CustomerCreateTOP {
  @Override
  public Consumer<Void> createCustomer(Customer customer) {
    return $ -> {
      CarrentalManager
          .customerBuilder()
          .name(customer.getName())
          .email(customer.getEmail())
          .age(customer.getAge())
          .build()
          .orElseThrow();

      setCustomer(CarrentalManager.customerBuilder());
    };
  }
}
