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

    @PostMapping(path="/pizza/")
    public @ResponseBody String addNewPizza (@RequestParam(name="session_string", required = true) String session_string, @RequestParam String name, @RequestParam String path, @RequestParam Integer price, @RequestParam Integer discount, @RequestParam boolean unavailable){
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

    @PutMapping(path = "pizza/")
    public @ResponseBody Pizza updatePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id, @RequestParam String name,
                                           @RequestParam String picture_path, @RequestParam Integer price,
                                           @RequestParam Integer discount_percent, @RequestParam boolean unavailable){
        return pizzaManager.updatePizza(id,name,picture_path,price,discount_percent,unavailable);
    }

    @DeleteMapping(path = "pizza/")
    public @ResponseBody Pizza deletePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id){
        return pizzaManager.deletePizza(id);
    }

}
