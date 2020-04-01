package hu.onlinepizzeria.server.web;

import hu.onlinepizzeria.server.dao.PizzaRepo;
import hu.onlinepizzeria.server.service.PizzaManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class WebConfig {

    @Bean
    public PizzaManager pizzaManager(PizzaRepo repository){
        return new PizzaManager(repository);
    }

}
