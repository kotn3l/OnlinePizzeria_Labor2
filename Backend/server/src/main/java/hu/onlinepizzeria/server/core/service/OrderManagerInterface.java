package hu.onlinepizzeria.server.core.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.model.*;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Map;

public interface OrderManagerInterface {
    public String addNewOrder(Map<String, Object> order);
    public Iterable<PayMethod> getPayMethods();
    public Iterable<DeliveryCities> getDeliveryCities();
    public ArrayList<ScheduledPizza> getPreparedPizzas();
    public String pizzaPrepared(Integer orderPizza);
    public ArrayList<ObjectNode> getReadyOrders();
}
