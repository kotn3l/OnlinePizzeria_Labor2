package hu.onlinepizzeria.server.web;

import hu.onlinepizzeria.server.core.service.PizzaManagerInterface;
import hu.onlinepizzeria.server.dao.IngredientRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import hu.onlinepizzeria.server.dao.SchedulingRepo;
import hu.onlinepizzeria.server.service.PizzaManager;
import hu.onlinepizzeria.server.service.SchedulingManager;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class WebConfig {

    @Bean
    public PizzaManager pizzaManager(PizzaRepo repository, IngredientRepo ingredientRepo){
        return new PizzaManager(repository, ingredientRepo);
    }

    @Bean
    public SchedulingManager schedulingManager(SchedulingRepo repository){
        return new SchedulingManager(repository);
    }
}
