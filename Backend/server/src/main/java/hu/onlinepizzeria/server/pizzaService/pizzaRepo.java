package hu.onlinepizzeria.server.pizzaService;

import hu.onlinepizzeria.server.core.model.Pizza;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface pizzaRepo extends CrudRepository<Pizza, Integer> {
    @Query(value="SELECT * FROM pizza WHERE discount_percent > 0 and unavailable = false", nativeQuery = true)
    List<Pizza> getDiscountedPizza();

    @Query(value = "SELECT * FROM pizza where unavailable = false", nativeQuery = true)
    List<Pizza> getVisiblePizza();

    @Modifying
    @Transactional
    @Query(value = "UPDATE pizza SET name=:name,price=:price,picture_path=:path,discount_percent=:percent,unavailable=:unavailable WHERE id=:id", nativeQuery = true)
    void updatePizza(Integer id, String name, Integer price, String path, Integer percent, boolean unavailable);
}
