package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.service.PizzaManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class PizzaController {

    private PizzaManager pizzaManager;

    public PizzaController(PizzaManager pizzaManager) {
        this.pizzaManager = pizzaManager;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewPizza (@RequestParam String name, @RequestParam String path, @RequestParam Integer price, @RequestParam Integer discount, @RequestParam boolean unavailable){
        return pizzaManager.addNewPizza(name, path, price, discount, unavailable);
    }

    @GetMapping(path="/pizza")
    public @ResponseBody Iterable<Pizza> getAllPizzas() {
        return pizzaManager.getAllPizzas();
    }

    @GetMapping(path="/pizza/discount")
    public @ResponseBody Iterable<Pizza> getDiscountedPizzas() {
        return pizzaManager.getDiscountedPizzas();
    }

    @PutMapping(path = "pizza/{id}")
    public @ResponseBody Pizza updatePizza(@PathVariable(value = "id") Integer id, @RequestParam String name,
                                           @RequestParam String picture_path, @RequestParam Integer price,
                                           @RequestParam Integer discount_percent, @RequestParam boolean unavailable){
        return pizzaManager.updatePizza(id,name,picture_path,price,discount_percent,unavailable);
    }

    @DeleteMapping(path = "pizza/{id}")
    public @ResponseBody Pizza deletePizza(@PathVariable(value = "id") Integer id){
        return pizzaManager.deletePizza(id);
    }

}
