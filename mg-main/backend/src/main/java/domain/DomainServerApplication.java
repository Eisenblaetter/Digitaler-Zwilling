/* (c) https://github.com/MontiCore/monticore */

package domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import jakarta.annotation.PostConstruct;

import java.io.IOException;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
        scanBasePackages = {"umlp.backendrte.service.websocket", "umlp.backendrte.service.rest", "domain.monitoring"}
)
public class DomainServerApplication extends DomainServerApplicationTOP {
    public static void main(String[] args) {
        SpringApplication.run(DomainServerApplication.class, args);
    }

    @PostConstruct
    @Override
    public void init() throws IOException {
        super.init();
    }
}
