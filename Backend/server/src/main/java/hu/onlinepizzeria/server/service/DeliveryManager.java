package hu.onlinepizzeria.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.exceptions.InvalidData;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.model.Delivery;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.OrderedPizza;
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

    @Autowired
    ObjectMapper mapper;

    public DeliveryManager(DeliveryRepo repo) {
        this.deliveryRepo = repo;
    }

    @Override
    public Iterable<User> getAllDeliveryGuys() {
        Iterable<Integer> deliveryGuys = deliveryRepo.getAllDeliveryGuys();
        return userRepo.findAllById(deliveryGuys);
    }

    @Override
    public String addNewDelivery(Integer deliveryGuyId, Iterable<Integer> orderId) throws InvalidId, InvalidData {
        Integer turn = null;
        if (!deliveryRepo.existsById(deliveryGuyId)){
            throw new InvalidId("delivery",deliveryGuyId.toString());
        }
        try {
            turn = deliveryRepo.getMaxTurn() + 1;
        } catch (Exception e) {
            //e.printStackTrace();
            turn = 1;
        }
        User del = userRepo.findById(deliveryGuyId).get();
        int orderCount = 1;
        for (Integer order_id : orderId) {
            if (!orderRepo.existsById(order_id)){
                throw new InvalidId("order", order_id.toString());
            }
            if (orderCount > 10){
                throw new InvalidData("Egy deliveryhez nem tartozhat 10nél több rendelés!");
            }
            Order ord = orderRepo.getOrderById(order_id).get(0);
            deliveryRepo.save(new Delivery(ord, del, turn));
            orderCount++;
        }
        return "saved";
    }

    @Override
    public List<ObjectNode> getDeliveriesByDeliveryGuy(Integer deliveryGuyId) {
        Map<Integer, Iterable<Order>> result = new HashMap<>();
        List<Integer> turns = deliveryRepo.getDeliveriesByDelivery_man(deliveryGuyId);
        List<ObjectNode> response = new ArrayList<>();
        for (Integer turn: turns) {
            ObjectNode deliveryNode = mapper.createObjectNode();
            deliveryNode.put("delivery_id", turn);
            List<Integer> orders = deliveryRepo.getOrdersByTurn(turn);
            List<ObjectNode> orderObjs = new ArrayList<>();
            for (Integer order : orders) {
                Order o = orderRepo.getOrderById(order).get(0);
                if(o.getState() == 3) // it's delivered so skip
                    continue;
                ObjectNode addressNode = mapper.createObjectNode();
                addressNode.put("order_id", o.getId());
                List<String> pNames = new ArrayList<>();
                for (OrderedPizza p: o.getoPizza()) {
                        pNames.add(p.getPizza().getName());
                }
                ArrayNode array = mapper.valueToTree(pNames);
                addressNode.putArray("pizzas").addAll(array);
                addressNode.put("city", o.getCity().getName());
                addressNode.put("street", o.getStreet());
                addressNode.put("house_number", o.getHouse_number());
                addressNode.put("other", o.getOther());
                addressNode.put("comment", o.getComment());
                deliveryNode.set("orders", addressNode);
                //deliveryNode.set("orders", )
                orderObjs.add(addressNode);
            }
            if (!orderObjs.isEmpty()) {
                deliveryNode.putArray("orders").addAll(orderObjs);
                response.add(deliveryNode);
            }
        }
        return response;
    }

    @Override
    public boolean updateDelivery(Integer deliveryId) throws InvalidId {
        // if it's not a valid delivery id then return false
        // or if it's already delivered also return false
        if (!deliveryRepo.existsById(deliveryId)){
            throw new InvalidId("delivery", deliveryId.toString());
        }
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
