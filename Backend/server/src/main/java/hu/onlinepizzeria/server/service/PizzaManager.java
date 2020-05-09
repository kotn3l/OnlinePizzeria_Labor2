package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.Ingredient;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.PizzaManagerInterface;
import hu.onlinepizzeria.server.dao.IngredientRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
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
    public String addNewPizza (Map<String, Object> pizza) throws InvalidParameterException {
        ArrayList<String> pIngredients = (ArrayList<String>)pizza.get("ingredients");
        if(pIngredients.size() <= 1) {
            throw new InvalidParameterException("A pizza must have more than one ingredient");
        }
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
        savePizzaIngredients(p.getId(), pizzaIngredients, false);

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
    public Pizza updatePizza(Integer id, Map<String, Object> pizza) throws InvalidParameterException {
        ArrayList<String> pIngredients = (ArrayList<String>)pizza.get("ingredients");
        if(pIngredients.size() <= 1) {
            throw new InvalidParameterException("A pizza must have more than one ingredient");
        }
        ArrayList<String> pizzaIngredients = new ArrayList<>(pIngredients);

        Pizza p = new Pizza();
        p.setId(id);
        p.setName(pizza.get("name").toString());
        p.setPicture_path(pizza.get("picture").toString());
        p.setPrice(Integer.parseInt(pizza.get("price").toString()));
        p.setDiscount_percent(Integer.parseInt(pizza.get("discount_price").toString()));
        p.setUnavailable(false);
        pizzaRepo.updatePizza(id, p.getName(), p.getPrice(), p.getPicture_path(), p.getDiscount_percent(), p.isUnavailable());

        ingredientCheck(pIngredients);
        saveIngredients(pIngredients);
        savePizzaIngredients(p.getId(), pizzaIngredients, true);
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
        if (currentIngredients.size() > 0) {
            for (Ingredient ci : currentIngredients
            ) {
                if (temp.contains(ci.getName())) {
                    temp.remove(ci.getName());
                }

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

    public void savePizzaIngredients(Integer pizzaId, ArrayList<String> pizzaIngredients, boolean update){
        if (update){
            ingredientRepo.deletePizzaIngredients(pizzaId);
        }
        ArrayList<Ingredient> currentIngredients = ingredientRepo.getIngredients();
        for (Ingredient ci : currentIngredients
        ) {
            if (pizzaIngredients.contains(ci.getName())) {
                ingredientRepo.addIngredientAndPizza(pizzaId, ci.getId());
            }
        }
    }
}
