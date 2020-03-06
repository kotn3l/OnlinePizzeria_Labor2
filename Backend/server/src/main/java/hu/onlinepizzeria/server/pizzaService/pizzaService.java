package hu.onlinepizzeria.server.pizzaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        // This returns a JSON or XML with the users
        return pizzaRepo.findAll();
    }





}
