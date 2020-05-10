package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
    @Query(value="SELECT * FROM customer", nativeQuery = true)
    ArrayList<Customer> getCustomers();

    @Query(value = "SELECT * FROM customer where id=:id", nativeQuery = true)
    Customer getCustomerById(Integer id);
}
