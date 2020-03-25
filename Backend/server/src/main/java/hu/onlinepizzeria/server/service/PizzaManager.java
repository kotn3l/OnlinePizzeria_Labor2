package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.PizzaManagerInterface;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class PizzaManager implements PizzaManagerInterface {

    @Autowired
    private PizzaRepo pizzaRepo;

    public PizzaManager(PizzaRepo repository) {
        this.pizzaRepo = repository;
    }

    @Override
    public String addNewPizza (String name, String path, Integer price, Integer discount, boolean unavailable){
        Pizza p = new Pizza();
        p.setName(name);
        p.setPicture_path(path);
        p.setPrice(price);
        p.setDiscount_percent(discount);
        p.setUnavailable(unavailable);
        pizzaRepo.save(p);
        return "Saved";
    }

    @Override
    public Iterable<Pizza> getAllPizzas() {
        return pizzaRepo.getVisiblePizza();
    }

    @Override
    public Iterable<Pizza> getDiscountedPizzas() {
        return pizzaRepo.getDiscountedPizza();
    }

    @Override
    public Pizza updatePizza(Integer id, String name, String picture_path, Integer price, Integer discount_percent, boolean unavailable){
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

    @Override
    public Pizza deletePizza(Integer id){
        Pizza p = pizzaRepo.findById(id).orElse(null);
        if (p != null) {
            pizzaRepo.updatePizza(id, p.getName(), p.getPrice(), p.getPicture_path(), p.getDiscount_percent(), true);
        }
        return p;
    }
}
