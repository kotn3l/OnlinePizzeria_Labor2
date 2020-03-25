package hu.onlinepizzeria.server.pizzaService;

import hu.onlinepizzeria.server.core.model.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api")
public class pizzaService {
    @Autowired
    private hu.onlinepizzeria.server.pizzaService.pizzaRepo pizzaRepo;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewPizza (@RequestParam String name
            , @RequestParam String path, @RequestParam Integer price, @RequestParam Integer discount, @RequestParam boolean unavailable) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Pizza p = new Pizza();
        p.setName(name);
        p.setPicture_path(path);
        p.setPrice(price);
        p.setDiscount_percent(discount);
        p.setUnavailable(unavailable);
        pizzaRepo.save(p);
        return "Saved";

    }

    @GetMapping(path="/pizza")
    public @ResponseBody Iterable<Pizza> getAllPizzas() {
        return pizzaRepo.getVisiblePizza();
    }

    //get discounted pizzas
    @GetMapping(path="/pizza/discount")
    public @ResponseBody Iterable<Pizza> getDiscountedPizzas() {
        return pizzaRepo.getDiscountedPizza();
    }

    //update pizza
    @PutMapping(path = "pizza/{id}")
    public @ResponseBody Pizza updatePizza(@PathVariable(value = "id") Integer id, @RequestParam String name,
                                                     @RequestParam String picture_path, @RequestParam Integer price,
                                                     @RequestParam Integer discount_percent, @RequestParam boolean unavailable){

        Pizza p = new Pizza();
        p.setId(id);
        p.setName(name);
        p.setPicture_path(picture_path);
        p.setPrice(price);
        p.setDiscount_percent(discount_percent);
        p.setUnavailable(unavailable);

        pizzaRepo.updatePizza(id,name,price,picture_path,discount_percent,unavailable);
        return p;
    }

    //delete pizza (basically another update, change unavailability to 1)
    @DeleteMapping(path = "pizza/{id}")
    public @ResponseBody Pizza deletePizza(@PathVariable(value = "id") Integer id){

        Pizza p = pizzaRepo.findById(id).orElse(null);

        if (p != null) {
            pizzaRepo.updatePizza(id, p.getName(), p.getPrice(), p.getPicture_path(), p.getDiscount_percent(), true);
        }

        return p;
    }
}
