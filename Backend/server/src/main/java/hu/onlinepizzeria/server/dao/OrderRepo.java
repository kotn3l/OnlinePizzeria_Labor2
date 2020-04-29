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
    @Query(value="INSERT INTO orders (customer_id, city, street, house_number, other, comment, pay_method, deadline, state, delivered) " +
            "VALUES (:customer_id, :city, :street, :house_number, :other, :comment, :pay_method, :deadline, :state, :delivered)", nativeQuery = true)
    void addNewOrder(Integer customer_id, Integer city, String street, Integer house_number, String other, String comment, Integer pay_method, Timestamp deadline, Integer state, Timestamp delivered);

    @Query(value="SELECT * FROM orders WHERE id = :order_id", nativeQuery = true)
    ArrayList<Order> getOrderById(Integer order_id);

    @Query(value="SELECT id FROM orders ORDER BY deadline DESC", nativeQuery = true)
    ArrayList<Integer> getOrderInOrder();

    @Modifying
    @Transactional
    @Query(value="UPDATE order_pizza SET done=1 WHERE pizza_id=:ordered_pizza_id AND done=0 LIMIT 1", nativeQuery = true)
    void pizzaPrepared(Integer ordered_pizza_id);

    @Query(value="SELECT DISTINCT order_id FROM order_pizza WHERE done = 1", nativeQuery = true)
    ArrayList<Integer> donePizzas();

    @Query(value="SELECT DISTINCT order_id FROM order_pizza WHERE done = 0 AND order_id=:order_id", nativeQuery = true)
    ArrayList<Integer> checkOrder(Integer order_id);

    @Query(value="SELECT pizza_id FROM order_pizza", nativeQuery = true)
    ArrayList<Integer> prepOrderPizzas();

    @Query(value="SELECT pizza_id FROM order_pizza WHERE order_id=:order_id", nativeQuery = true)
    ArrayList<Integer> prepOrderPizzaById(Integer order_id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO order_pizza(order_id, pizza_id, done) VALUES (:order_id, :ordered_pizza_id, 0)", nativeQuery = true)
    void addOrderedPizza(Integer ordered_pizza_id, Integer order_id);
}
