package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.service.PizzaManager;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PizzaController {

    private PizzaManager pizzaManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public PizzaController(PizzaManager pizzaManager) {
        this.pizzaManager = pizzaManager;
    }

    @PostMapping(path="/pizza/")
    public @ResponseBody
    ResponseEntity addNewPizza (@RequestParam(name="session_string", required = true) String session_string, @RequestPart("pizza") Map<String, Object> pizza, @RequestPart("file")
            MultipartFile multipart){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) {
                return new ResponseEntity(pizzaManager.addNewPizza(pizza, multipart), HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (InvalidParameterException ipe){
            return new ResponseEntity(ipe.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PutMapping(path = "/pizza/", consumes = {"multipart/form-data"})
    public @ResponseBody ResponseEntity updatePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id, @RequestPart("pizza") Map<String, Object> pizza,
                                                    @RequestPart("file") MultipartFile multipart) {
        try {
            if (jwtTokenProvider.isAdmin(session_string)) {
                return new ResponseEntity(pizzaManager.updatePizza(id, pizza, multipart), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/pizza/")
    public @ResponseBody ResponseEntity deletePizza(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="pizza_id", required = true) Integer id){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) {
                return new ResponseEntity(pizzaManager.deletePizza(id), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
