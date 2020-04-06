package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.Pizza;

import java.util.Map;

public interface PizzaManagerInterface {
    public String addNewPizza(Map<String, Object> pizza);
    public Iterable<Pizza> getAllPizzas();
    public Iterable<Pizza> getDiscountedPizzas();
    public Pizza updatePizza(Integer id, String name, String picture_path, Integer price, Integer discount_percent, boolean unavailable);
    public Pizza deletePizza(Integer id);
}