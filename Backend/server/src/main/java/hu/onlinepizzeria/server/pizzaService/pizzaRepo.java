package hu.onlinepizzeria.server.pizzaService;

import org.springframework.data.repository.CrudRepository;

public interface pizzaRepo extends CrudRepository<Pizza, Integer> {

}
