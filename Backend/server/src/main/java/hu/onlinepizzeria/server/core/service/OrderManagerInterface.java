package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.*;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Map;

public interface OrderManagerInterface {
    public String addNewOrder(Map<String, Object> order);
    public Iterable<PayMethod> getPayMethods();
    public Iterable<DeliveryCities> getDeliveryCities();
    public ArrayList<ScheduledPizza> getPreparedPizzas();
    public String pizzaPrepared(Integer ordered_pizza_id);
    public Iterable<Order> getReadyOrders();
}
