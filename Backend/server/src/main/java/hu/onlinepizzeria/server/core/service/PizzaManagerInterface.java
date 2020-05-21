package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.Pizza;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PizzaManagerInterface {
    public String addNewPizza(Map<String, Object> pizza, MultipartFile multipart) throws IOException;
    public Iterable<Pizza> getAllPizzas();
    public Iterable<Pizza> getDiscountedPizzas();
    public String updatePizza(Integer id, Map<String, Object> pizza, MultipartFile multipart) throws IOException;
    public String deletePizza(Integer id);
}
