package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.DeliveryCities;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CityRepo extends CrudRepository<DeliveryCities, Integer> {
    @Query(value="SELECT * FROM delivery_cities", nativeQuery = true)
    ArrayList<DeliveryCities> getDeliveryCities();
}
