package hu.onlinepizzeria.server.pizzaService;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface pizzaRepo extends CrudRepository<Pizza, Integer> {
    @Query(value="SELECT * FROM pizza WHERE discount_percent > 0 and unavailable = false", nativeQuery = true)
    List<Pizza> getDiscountedPizza();

    @Query(value = "SELECT * FROM pizza where unavailable = false", nativeQuery = true)
    List<Pizza> getVisiblePizza();
}
