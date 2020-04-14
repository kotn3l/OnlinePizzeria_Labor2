package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.model.DeliveryCities;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.PayMethod;
import hu.onlinepizzeria.server.core.model.Pizza;

import java.util.Map;

public interface OrderManagerInterface {
    public String addNewOrder(Map<String, Object> order);
    public Iterable<PayMethod> getAllPayMethods();
    public Iterable<DeliveryCities> getAllDeliveryCities();
    public Iterable<Pizza> getPreparedPizzas();
    public String pizzaPrepared(Integer ordered_pizza_id);
    public Iterable<Order> getReadyOrders();
}
