package hu.onlinepizzeria.server.core.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.exceptions.InvalidData;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.model.User;

import java.util.List;

public interface DeliveryManagerInterface {
    public Iterable<User> getAllDeliveryGuys();
    public String addNewDelivery(Integer deliveryGuyId, Iterable<Integer> orderId) throws InvalidId, InvalidData;
    public List<ObjectNode> getDeliveriesByDeliveryGuy(Integer deliveryGuyId);
    public boolean updateDelivery(Integer deliveryId) throws InvalidId;
}
