package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.Ingredient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface IngredientRepo extends CrudRepository<Ingredient, Integer> {
    @Query(value="SELECT * FROM ingredient", nativeQuery = true)
    ArrayList<Ingredient> getIngredients();

    @Query(value="SELECT ingredient_id FROM pizza_ingredients WHERE pizza_id=:pizzaId", nativeQuery = true)
    ArrayList<Integer> getIngredientIdByPizza(Integer pizzaId);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO ingredient (name) VALUES (:name)", nativeQuery = true)
    void addIngredient(String name);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO pizza_ingredients (pizza_id, ingredient_id) VALUES (:pizzaId, :ingredientId)", nativeQuery = true)
    void addIngredientAndPizza(Integer pizzaId, Integer ingredientId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM pizza_ingredients WHERE pizza_id = :pizzaId", nativeQuery = true)
    void deletePizzaIngredients(Integer pizzaId);
}
