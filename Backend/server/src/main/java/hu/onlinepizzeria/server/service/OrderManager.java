package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.*;
import hu.onlinepizzeria.server.core.service.OrderManagerInterface;
import hu.onlinepizzeria.server.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private SchedulingRepo schedulingRepo;

    public OrderManager(OrderRepo orderRepo, PayMethodRepo payMethodRepo, CityRepo cityRepo, PizzaRepo pizzaRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.payMethodRepo = payMethodRepo;
        this.cityRepo = cityRepo;
        this.pizzaRepo = pizzaRepo;
        this.customerRepo = customerRepo;
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
        Integer customerIdCheck = checkUser(order.get("email").toString(), order.get("telephone").toString());
        Customer customer = new Customer();
        if (customerIdCheck == -1) {
            customer.setName(order.get("name").toString());
            customer.setEmail(order.get("email").toString());
            customer.setTelephone(order.get("telephone").toString());
            customerRepo.save(customer);
        } else {
            customer = customerRepo.getCustomerById(customerIdCheck);
        }
        Date date= new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, calculateDeadline(orderedPizzas.size()));
        Order currentOrder = new Order(customer, new DeliveryCities(Integer.parseInt(order.get("city").toString())), order.get("street").toString(), Integer.parseInt(order.get("house_number").toString()),
                order.get("other").toString(), order.get("comment").toString(), new PayMethod(Integer.parseInt(order.get("pay_method").toString())), new Timestamp(calendar.getTime().getTime()), 0, null);
        orderRepo.save(currentOrder);
        for (int i = 0; i < orderedPizzas.size(); i++) {
            orderRepo.addOrderedPizza(orderedPizzas.get(i), currentOrder.getId());
        }
        schedule();

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
                while (temp.remove(p.getId()));
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

    public Integer checkUser(String email, String telephone){
        ArrayList<Customer> customers = new ArrayList<Customer>(customerRepo.getCustomers());
        for (int i = 0; i < customers.size(); i++){
            if (customers.get(i).getEmail().equals(email) && customers.get(i).getTelephone().equals(telephone)){
                return customers.get(i).getId();
            }
        }
        return -1;
    }

    public Integer calculateDeadline(int orderedPizzaCount){
        int toReturn = 0;
        int minutes = 30;
        for (int i = 0; i < orderedPizzaCount; i++){
            toReturn += minutes;
            minutes = minutes / 2;
            if (minutes >= 15){
                minutes = 5;
            }
        }
        return toReturn;
    }

    public void schedule(){
        Integer active = schedulingRepo.getActiveAlgorithm();
        if (active == 1){
            scheduleOrderedPizzasDeadline();
        }
        else if (active == 2){
            //the other one
        } else {
            throw new InvalidParameterException("No scheduling is set to active! Please set scheduling either to 1 (order deadline oriented) or 2 (ingredient-oriented)");
        }
    }

    public void scheduleOrderedPizzasDeadline(){
        orderRepo.truncateScheduledPizzas();
        ArrayList<Integer> orderIdsDone = orderRepo.donePizzas();
        ArrayList<Integer> orderIdsInOrder = orderRepo.getOrderInOrderAsc();
        orderIdsInOrder.removeAll(orderIdsDone);
        ArrayList<Integer> orderPizzaIds = new ArrayList<>();
        for (Integer oi: orderIdsInOrder
        ) {
            orderPizzaIds.addAll(orderRepo.getOrderPizzaByOId(oi));
        }
        ArrayList<Integer> orderPizzaIdsDone = orderRepo.getOrderPizzaDone();
        orderPizzaIds.removeAll(orderPizzaIdsDone);
        Integer prepNum = 1;
        for (Integer opi: orderPizzaIds
             ) {
            orderRepo.addOrderPizza(opi, prepNum);
            prepNum++;
        }
    }
}
