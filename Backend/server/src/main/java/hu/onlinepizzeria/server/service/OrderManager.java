package hu.onlinepizzeria.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.exceptions.InvalidData;
import hu.onlinepizzeria.server.core.model.*;
import hu.onlinepizzeria.server.core.service.OrderManagerInterface;
import hu.onlinepizzeria.server.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.*;

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
    private SchedulingManager schedulingManager;

    @Autowired
    ObjectMapper mapper;

    public OrderManager(OrderRepo orderRepo, PayMethodRepo payMethodRepo, CityRepo cityRepo, PizzaRepo pizzaRepo, CustomerRepo customerRepo, SchedulingManager schedulingManager) {
        this.orderRepo = orderRepo;
        this.payMethodRepo = payMethodRepo;
        this.cityRepo = cityRepo;
        this.pizzaRepo = pizzaRepo;
        this.customerRepo = customerRepo;
        this.schedulingManager = schedulingManager;
        //schedulingManager.schedule();
    }

    @Override
    public String addNewOrder(Map<String, Object> order) throws InvalidData {
        ArrayList<Integer> orderedPizzas = (ArrayList<Integer>)order.get("order");
        if(!checkPizza(orderedPizzas)){
            throw new InvalidData("Nem megfelelő pizza: " + orderedPizzas.toString());
        }
        if(!checkCity(Integer.parseInt(order.get("city").toString()))){
            throw new InvalidData("Nem megfelelő város: " + order.get("city"));
        }
        if(!checkPayMethod(Integer.parseInt(order.get("pay_method").toString()))){
            throw new InvalidData("Nem megfelelő fizetési mód: " + order.get("pay_method").toString());
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
        schedulingManager.schedule();

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
    public ArrayList<ScheduledPizza> getPreparedPizzas() {
        ArrayList<Integer> orderPizzaByPrep = orderRepo.getOrderPizzaByPrep();
        ArrayList<ScheduledPizza> scheduledPizzas = new ArrayList<>();
        for (int i = 0; i < orderPizzaByPrep.size(); i++){
            scheduledPizzas.add(new ScheduledPizza(orderPizzaByPrep.get(i), i+1, pizzaRepo.getPizzaById(orderRepo.orderPizzaById(orderPizzaByPrep.get(i)))));
        }
        return scheduledPizzas;
    }

    @Override
    public String pizzaPrepared(Integer orderPizza) {
        orderRepo.pizzaPrepared(orderPizza);
        schedulingManager.schedule();
        return "Saved";
    }

    @Override
    public ArrayList<ObjectNode> getReadyOrders() {
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

        ObjectNode response = mapper.createObjectNode();
        ArrayList<ObjectNode> doneOrdersMap = new ArrayList<>();
        for (Order order: doneOrders){
            ObjectNode doneOrderMap = mapper.createObjectNode();
            doneOrderMap.put("order_id", order.getId());
            doneOrderMap.put("city", order.getCity().getName());
            doneOrderMap.put("street", order.getStreet());
            doneOrderMap.put("house_number", order.getHouse_number());
            doneOrderMap.put("other", order.getOther());
            doneOrderMap.put("pizza_count", orderRepo.pizzasPerOrder(order.getId()));
            doneOrdersMap.add(doneOrderMap);
        }
        return doneOrdersMap;
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

}
