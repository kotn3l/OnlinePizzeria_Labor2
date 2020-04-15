package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.PayMethod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PayMethodRepo extends CrudRepository<PayMethod, Integer> {
    @Query(value="SELECT * FROM pay_method", nativeQuery = true)
    ArrayList<PayMethod> getPayMethods();
}
