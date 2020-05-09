package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.Delivery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliveryRepo extends CrudRepository<Delivery, Integer> {
    @Query(value = "SELECT turn from delivery_man_order where delivery_man=:deliveryGuyId" ,
            nativeQuery = true)
    List<Integer> getDeliveriesByDelivery_man(Integer deliveryGuyId);

    @Query(value = "SELECT order_id from delivery_man_order where turn=:turn ", nativeQuery = true)
    List<Integer> getOrdersByTurn(Integer turn);

    @Modifying
    @Transactional
    @Query(value = "update orders SET state=1 where id=:deliveryId", nativeQuery = true)
    void setDeliveryDone(Integer deliveryId);

    @Query(value = "SELECT user_id from user_roles where roles='ROLE_DELIVERY'", nativeQuery = true)
    List<Integer> getAllDeliveryGuys();

    @Query(value = "SELECT * FROM delivery_man_order ORDER BY id DESC LIMIT 0, 1", nativeQuery = true)
    Integer getMaxTurn();
}
