/* (c) https://github.com/MontiCore/monticore */

package digitaltwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication(
    exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
    scanBasePackages={"umlp.backendrte.service.websocket", "umlp.backendrte.service.rest", "digitaltwin.service"}
)
public class DigitalTwinServerApplication extends DigitalTwinServerApplicationTOP {

  public static void main(String[] args) {
    SpringApplication.run(DigitalTwinServerApplication.class, args);
  }

  @PostConstruct
  @Override
  public void init() throws IOException {
    super.init();
    initEntities();
  }

  protected void initEntities() {
    // TODO: initialize machines, etc.
  }
}