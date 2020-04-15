package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.DeliveryCities;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.PayMethod;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.OrderManagerInterface;
import hu.onlinepizzeria.server.dao.CityRepo;
import hu.onlinepizzeria.server.dao.OrderRepo;
import hu.onlinepizzeria.server.dao.PayMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class OrderManager implements OrderManagerInterface {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PayMethodRepo payMethodRepo;

    @Autowired
    private CityRepo cityRepo;

    public OrderManager(OrderRepo orderRepo, PayMethodRepo payMethodRepo, CityRepo cityRepo) {
        this.orderRepo = orderRepo;
        this.payMethodRepo = payMethodRepo;
        this.cityRepo = cityRepo;
    }

    @Override
    public String addNewOrder(Map<String, Object> order) {
        return null;
    }

    @Override
    public Iterable<PayMethod> getPayMethods() {
        return null;
    }

    @Override
    public Iterable<DeliveryCities> getDeliveryCities() {
        return null;
    }

    @Override
    public Iterable<Pizza> getPreparedPizzas() {
        return null;
    }

    @Override
    public String pizzaPrepared(Integer ordered_pizza_id) {
        return null;
    }

    @Override
    public Iterable<Order> getReadyOrders() {
        return null;
    }
}
