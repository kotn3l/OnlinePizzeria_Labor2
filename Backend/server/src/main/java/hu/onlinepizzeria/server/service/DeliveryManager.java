package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.Delivery;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.User;
import hu.onlinepizzeria.server.core.service.DeliveryManagerInterface;
import hu.onlinepizzeria.server.dao.DeliveryRepo;
import hu.onlinepizzeria.server.dao.OrderRepo;
import hu.onlinepizzeria.server.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.*;

public class DeliveryManager implements DeliveryManagerInterface {
    @Autowired
    DeliveryRepo deliveryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderRepo orderRepo;

    public DeliveryManager(DeliveryRepo repo) {
        this.deliveryRepo = repo;
    }

    @Override
    public Iterable<User> getAllDeliveryGuys() {
        Iterable<Integer> deliveryGuys = deliveryRepo.getAllDeliveryGuys();
        return userRepo.findAllById(deliveryGuys);
    }

    @Override
    public String addNewDelivery(Integer deliveryGuyId, Iterable<Integer> orderId) {
        Integer turn = null;
        try {
            turn = deliveryRepo.getMaxTurn() + 1;
        } catch (Exception e) {
            e.printStackTrace();
            turn = 1;
        }
        User del = userRepo.findById(deliveryGuyId).get();
        for (Integer order_id : orderId) {
            Order ord = orderRepo.getOrderById(order_id).get(0);
            deliveryRepo.save(new Delivery(ord, del, turn));
        }
        return "saved";
    }

    @Override
    public Map<Integer, Iterable<Order>> getDeliveriesByDeliveryGuy(Integer deliveryGuyId) {
        Map<Integer, Iterable<Order>> result = new HashMap<>();
        List<Integer> turns = deliveryRepo.getDeliveriesByDelivery_man(deliveryGuyId);
        for (Integer turn: turns
             ) {
            List<Integer> orders = deliveryRepo.getOrdersByTurn(turn);
            List<Order> orderObjs = new ArrayList<>();
            for (Integer order : orders
                 ) {
                orderObjs.add(orderRepo.getOrderById(order).get(0));
            }
            result.put(turn, orderObjs);
        }
        return result;
    }

    @Override
    public boolean updateDelivery(Integer deliveryId) {
        // if it's not a valid delivery id then return false
        // or if it's already delivered also return false
        List<Integer> orders = deliveryRepo.getOrdersByTurn(deliveryId);
        Date date = new Date();

        for ( Integer ord : orders) {
            Order o = orderRepo.getOrderById(ord).get(0);
            o.setState(3);
            o.setDelivered(new Timestamp(date.getTime()));
            orderRepo.save(o);
        }
        //deliveryRepo.setDeliveryDone(deliveryId);
        return true;
    }
}
