package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.DeliveryCities;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.PayMethod;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.OrderManagerInterface;
import hu.onlinepizzeria.server.dao.CityRepo;
import hu.onlinepizzeria.server.dao.OrderRepo;
import hu.onlinepizzeria.server.dao.PayMethodRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class OrderManager implements OrderManagerInterface {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PayMethodRepo payMethodRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private PizzaRepo pizzaRepo;

    Date date= new Date();
    long time = date.getTime();

    public OrderManager(OrderRepo orderRepo, PayMethodRepo payMethodRepo, CityRepo cityRepo, PizzaRepo pizzaRepo) {
        this.orderRepo = orderRepo;
        this.payMethodRepo = payMethodRepo;
        this.cityRepo = cityRepo;
        this.pizzaRepo = pizzaRepo;
    }

    @Override
    public String addNewOrder(Map<String, Object> order) {
        ArrayList<Integer> orderedPizzas = (ArrayList<Integer>)order.get("order");
        //customer_id comes from session that placed the order? from that we can get the name, telephone number, email, but why is it in the Map then TODO replace customer_id, TODO so something with delivered timestamp
        Order currentOrder = new Order(2, Integer.parseInt(order.get("city").toString()), order.get("street").toString(), Integer.parseInt(order.get("house_number").toString()),
                order.get("other").toString(), order.get("comment").toString(), Integer.parseInt(order.get("pay_method").toString()), new Timestamp(time), 0, null);
        orderRepo.addNewOrder(currentOrder.getCustomer_id(), currentOrder.getCity(), currentOrder.getStreet(), currentOrder.getHouse_number(), currentOrder.getOther(), currentOrder.getComment(),
                currentOrder.getPay_method(), currentOrder.getDeadline(), currentOrder.getState(), currentOrder.getDelivered());
        //order list contains pizza ids? need to put those in orderedpizza with the same order ID, right?
        for (Integer i: orderedPizzas
             ) {
            orderRepo.addOrderedPizza(i, currentOrder.getId()); //TODO for some reason the ID is null, throws exception cause id cannot be null
        }

        return "Saved";
    }

    @Override
    public Iterable<PayMethod> getPayMethods() {
        return payMethodRepo.getPayMethods();
    }

    @Override
    public Iterable<DeliveryCities> getDeliveryCities() {
        return cityRepo.getDeliveryCities();
    }

    @Override
    public Iterable<Pizza> getPreparedPizzas() {
        ArrayList<Integer> pizzaIds = orderRepo.preparedPizzas();
        ArrayList<Pizza> pizzas = new ArrayList<>();
        for (int i = 0; i <= pizzaIds.size(); i++){
            pizzas.add(pizzaRepo.getPizzaById(pizzaIds.get(i)));
        }
        return pizzas;
    }

    @Override
    public String pizzaPrepared(Integer ordered_pizza_id) {
        orderRepo.pizzaPrepared(ordered_pizza_id);
        return "Saved";
    }

    @Override
    public Iterable<Order> getReadyOrders() {
        //TODO get orders where all pizzas are done
        return null;
    }
}
