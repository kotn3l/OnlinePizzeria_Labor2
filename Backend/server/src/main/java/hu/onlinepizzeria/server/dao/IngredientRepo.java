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

    @Modifying
    @Transactional
    @Query(value="INSERT INTO ingredient (name) VALUES (:name)", nativeQuery = true)
    void addIngredient(String name);
}
