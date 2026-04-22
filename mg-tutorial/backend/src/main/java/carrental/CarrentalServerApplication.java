/* (c) https://github.com/MontiCore/monticore */

package carrental;

import mc.fenix.charts.gembarcharttypes.GemBarChartTypesDeSer;
import mc.fenix.charts.gemlinecharttypes.GemLineChartTypesDeSer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import umlp.backendrte.common.BaseClassObjectMapper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication(
    exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
    scanBasePackages={"umlp.backendrte.service.websocket", "umlp.backendrte.service.rest", "carrental.service"}
)
public class CarrentalServerApplication extends CarrentalServerApplicationTOP {

  public static void main(String[] args) {
    SpringApplication.run(CarrentalServerApplication.class, args);
  }

  @PostConstruct
  @Override
  public void init() throws IOException {
    super.init();
    initEntities();
  }

  protected void initEntities() {
    // TODO: GUI Basics - Create car objects (use make Ford, Toyota or VW to get images)
    /*SOL start Class GUI Basics*/
    Car ford = CarrentalManager.carBuilder()
        .name("GT")
        .make("Ford")
        .model("Mustang")
        .costPerDay(200.0f)
        .mileage(1000.0f)
        .build()
        .get();
    
    Car toyota = CarrentalManager.carBuilder()
        .name("LE")
        .make("Toyota")
        .model("Corolla")
        .costPerDay(25.0f)
        .mileage(61000.0f)
        .build()
        .get();
    
    Car vw = CarrentalManager.carBuilder()
        .name("Herbie")
        .make("VW")
        .model("Käfer")
        .costPerDay(40.0f)
        .mileage(2500.0f)
        .build()
        .get();
    /*SOL end Class GUI Basics*/
    // ToDo
    /*SOL start 01_ClassDiagramBasics.md*/
    Customer jim = CarrentalManager.customerBuilder() // returns CustomerBuilder
        .name("Jim Douglas")
        .email("incorrect_mail@example.com")
        .age(34)
        .build() // return Optional<Customer>
        .get();
    Customer dummy = CarrentalManager.customerBuilder()
        .name("Delete me")
        .email("delete@me.mail")
        .age(33)
        .build()
        .get();
    jim.getName();
    jim.setEmail("jim.douglas@example.com");
    CarrentalManager.deleteCustomer(dummy.getGemId());
    /*SOL end 01_ClassDiagramBasics.md*/
  }
}