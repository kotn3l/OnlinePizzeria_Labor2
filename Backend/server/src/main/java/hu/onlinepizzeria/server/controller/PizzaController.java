package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.service.PizzaManager;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class PizzaController {

    private PizzaManager pizzaManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public PizzaController(PizzaManager pizzaManager) {
        this.pizzaManager = pizzaManager;
    }
    // TODO Invalid values (400):
    //
    //    Price: Price can't be negative and must be greater than 500.
    //    Ingredients: A pizza must have more than one ingredient.
    //    Picture: Invalid file
    @PostMapping(path="/pizza/")
    public @ResponseBody
    ResponseEntity addNewPizza (@RequestParam(name="session_string", required = true) String session_string, @RequestBody Map<String, Object> pizza){
        if(jwtTokenProvider.isAdmin(session_string)) {
            return new ResponseEntity(pizzaManager.addNewPizza(pizza), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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
