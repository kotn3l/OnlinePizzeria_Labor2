package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.Ingredient;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.PizzaManagerInterface;
import hu.onlinepizzeria.server.dao.IngredientRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

public class PizzaManager implements PizzaManagerInterface {

    @Autowired
    private PizzaRepo pizzaRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    public PizzaManager(PizzaRepo repository, IngredientRepo ingredientRepo) {

        this.pizzaRepo = repository;
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public String addNewPizza (Map<String, Object> pizza){
        ArrayList<String> pIngredients = (ArrayList<String>)pizza.get("ingredients");
        ArrayList<String> pizzaIngredients = new ArrayList<>(pIngredients);

        Pizza p = new Pizza();
        p.setName(pizza.get("name").toString());
        p.setPicture_path(pizza.get("picture").toString());
        p.setPrice(Integer.parseInt(pizza.get("price").toString()));
        p.setDiscount_percent(0);
        p.setUnavailable(false);
        pizzaRepo.save(p);

        ingredientCheck(pIngredients);
        saveIngredients(pIngredients);
        savePizzaIngredients(p.getId(), pizzaIngredients);

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

    public ArrayList<String> ingredientCheck(ArrayList<String> pizzaIngredients){
        ArrayList<Ingredient> currentIngredients = ingredientRepo.getIngredients();
        ArrayList<String> temp = pizzaIngredients;
        for (Ingredient ci : currentIngredients
             ) {
            if (temp.contains(ci.getName())){
                temp.remove(ci.getName());
            }

        }
        return temp;
    }

    public void saveIngredients(ArrayList<String> pizzaIngredients){
        for (String s: pizzaIngredients
             ) {
            ingredientRepo.addIngredient(s);
        }
    }

    public void savePizzaIngredients(Integer pizzaId, ArrayList<String> pizzaIngredients){
        ArrayList<Ingredient> currentIngredients = ingredientRepo.getIngredients();
        for (Ingredient ci : currentIngredients
        ) {
            if (pizzaIngredients.contains(ci.getName())) {
                ingredientRepo.addIngredientAndPizza(pizzaId, ci.getId());
            }
        }
    }
}
