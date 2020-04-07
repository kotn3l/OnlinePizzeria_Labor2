package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.service.PizzaManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class PizzaController {

    private PizzaManager pizzaManager;

    public PizzaController(PizzaManager pizzaManager) {
        this.pizzaManager = pizzaManager;
    }

    @PostMapping(path="/pizza/")
    public @ResponseBody String addNewPizza (@RequestParam(name="session_string", required = true) String session_string, @RequestBody Map<String, Object> pizza){
        return pizzaManager.addNewPizza(pizza);
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
    public @ResponseBody Pizza updatePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id, @RequestBody Map<String, Object> pizza){
        return pizzaManager.updatePizza(id, pizza);
    }

    @DeleteMapping(path = "pizza/")
    public @ResponseBody Pizza deletePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id){
        return pizzaManager.deletePizza(id);
    }

}
