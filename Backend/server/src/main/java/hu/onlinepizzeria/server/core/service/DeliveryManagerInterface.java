package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.User;

import java.util.Map;

public interface DeliveryManagerInterface {
    public Iterable<User> getAllDeliveryGuys();
    public String addNewDelivery(Integer deliveryGuyId, Iterable<Integer> orderId);
    public Map<Integer, Iterable<Order>> getDeliveriesByDeliveryGuy(Integer deliveryGuyId);
    public boolean updateDelivery(Integer deliveryId);
}
