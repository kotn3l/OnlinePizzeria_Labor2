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

import java.security.InvalidParameterException;
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
    public String addNewOrder(Map<String, Object> order) throws InvalidParameterException {
        ArrayList<Integer> orderedPizzas = (ArrayList<Integer>)order.get("order");
        if(!checkPizza(orderedPizzas)){
            throw new InvalidParameterException("A pizza does not exist");
        }
        if(!checkCity(Integer.parseInt(order.get("city").toString()))){
            throw new InvalidParameterException("Invalid city");
        }
        if(!checkPayMethod(Integer.parseInt(order.get("pay_method").toString()))){
            throw new InvalidParameterException("Invalid pay method");
        }
        //customer_id comes from session that placed the order? from that we can get the name, telephone number, email, but why is it in the Map then TODO replace customer_id, TODO do something with delivered timestamp
        Order currentOrder = new Order(2, new DeliveryCities(Integer.parseInt(order.get("city").toString())), order.get("street").toString(), Integer.parseInt(order.get("house_number").toString()),
                order.get("other").toString(), order.get("comment").toString(), new PayMethod(Integer.parseInt(order.get("pay_method").toString())), new Timestamp(time), 0, null);
        orderRepo.save(currentOrder);
        //order list contains pizza ids? need to put those in orderedpizza with the same order ID, right?
        for (int i = 0; i < orderedPizzas.size(); i++) {
            orderRepo.addOrderedPizza(orderedPizzas.get(i), currentOrder.getId());
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
        ArrayList<Integer> pizzaIds = new ArrayList<>();
        ArrayList<Integer> orderIds = orderRepo.getOrderInOrder(); //in order
        ArrayList<Pizza> pizzas = new ArrayList<>();
        for (int i = 0; i < orderIds.size(); i++){
            pizzaIds.addAll(orderRepo.prepOrderPizzaById(orderIds.get(i)));
            for (int j = 0; j < pizzaIds.size(); j++){
                pizzas.add(pizzaRepo.getPizzaById(pizzaIds.get(j)));
            }
            pizzaIds.clear();
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
        ArrayList<Integer> orderIdsThatHasDonePizzas = orderRepo.donePizzas();
        ArrayList<Integer> orderIdsThatHasUndonePizzas = new ArrayList<>();
        ArrayList<Order> doneOrders = new ArrayList<>();
        for (int i = 0; i < orderIdsThatHasDonePizzas.size(); i++){
            orderIdsThatHasUndonePizzas.addAll(orderRepo.checkOrder(orderIdsThatHasDonePizzas.get(i)));
        }

        orderIdsThatHasDonePizzas.removeAll(orderIdsThatHasUndonePizzas);

        for (int i = 0; i < orderIdsThatHasDonePizzas.size(); i++){
            doneOrders.addAll(orderRepo.getOrderById(orderIdsThatHasDonePizzas.get(i)));
        }

        return doneOrders;
    }

    public boolean checkPizza(ArrayList<Integer> orderedPizzas){
        ArrayList<Integer> temp = new ArrayList<Integer>(orderedPizzas);
        ArrayList<Pizza> pizzas = new ArrayList<Pizza>(pizzaRepo.getVisiblePizza());
        for (Pizza p: pizzas
             ) {
            if (temp.contains(p.getId())) {
                temp.remove(p.getId());
            }
        }
        if (temp.size() > 0){
            return false;
        }
        return true;
    }

    public boolean checkPayMethod(Integer payMethod){
        ArrayList<Integer> payMethods = new ArrayList<Integer>(payMethodRepo.getPayMethodsId());
        if (payMethods.contains(payMethod)){
            return true;
        }
        return false;
    }

    public boolean checkCity(Integer city){
        ArrayList<Integer> cities = new ArrayList<Integer>(cityRepo.getDeliveryCitiesId());
        if (cities.contains(city)){
            return true;
        }
        return false;
    }
}
