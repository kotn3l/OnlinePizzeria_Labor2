package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.OrderedPizza;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface OrderRepo extends CrudRepository<Order, Integer> {
    @Modifying
    @Transactional
    @Query(value="INSERT INTO order (customer_id, city, street, house_number, other, comment, pay_method, deadline, state, delivered) " +
            "VALUES (:customer_id, :city, :street, :house_number, :other, :comment, :pay_method, :deadline, :state, :delivered)", nativeQuery = true)
    void addNewOrder(Integer customer_id, Integer city, String street, Integer house_number, String other, String comment, Integer pay_method, Timestamp deadline, Integer state, Timestamp delivered);

    @Query(value="SELECT * FROM order WHERE id IN (SELECT order_id FROM order_pizza where done = 1)", nativeQuery = true) //is this right?
    ArrayList<Order> getReadyOrders();

    @Modifying
    @Transactional
    @Query(value="UPDATE order_pizza SET done=1 WHERE pizza_id=:ordered_pizza_id", nativeQuery = true)
    void pizzaPrepared(Integer ordered_pizza_id);

    @Query(value="SELECT * FROM order_pizza WHERE", nativeQuery = true) //TODO
    ArrayList<OrderedPizza> preparedPizzas();

    @Modifying
    @Transactional
    @Query(value="INSERT INTO order_pizza(order_id, pizza_id, done) VALUES (:order_id, :ordered_pizza_id, 0)", nativeQuery = true)
    void addOrderedPizza(Integer ordered_pizza_id, Integer order_id);
}
