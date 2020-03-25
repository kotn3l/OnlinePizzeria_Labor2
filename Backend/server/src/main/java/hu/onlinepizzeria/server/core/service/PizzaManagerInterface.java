package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.Pizza;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface PizzaManagerInterface {
    public String addNewPizza(String name, String path, Integer price, Integer discount, boolean unavailable);
    public Iterable<Pizza> getAllPizzas();
    public Iterable<Pizza> getDiscountedPizzas();
    public Pizza updatePizza(Integer id, String name, String picture_path, Integer price, Integer discount_percent, boolean unavailable);
    public Pizza deletePizza(Integer id);
}
