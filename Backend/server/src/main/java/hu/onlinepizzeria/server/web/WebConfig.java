package hu.onlinepizzeria.server.web;

import hu.onlinepizzeria.server.dao.*;
import hu.onlinepizzeria.server.service.DeliveryManager;
import hu.onlinepizzeria.server.service.OrderManager;
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

    @Bean
    public OrderManager orderManager(OrderRepo orderRepo, PayMethodRepo payMethodRepo, CityRepo cityRepo, PizzaRepo pizzaRepo, CustomerRepo customerRepo, SchedulingRepo schedulingRepo){
        return new OrderManager(orderRepo, payMethodRepo, cityRepo, pizzaRepo, customerRepo, schedulingRepo);
    }
    @Bean
    public DeliveryManager deliveryManager(DeliveryRepo repository) { return new DeliveryManager(repository); }
}
