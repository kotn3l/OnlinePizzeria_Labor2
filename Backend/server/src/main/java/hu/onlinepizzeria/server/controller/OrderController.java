package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.DeliveryCities;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.core.model.PayMethod;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.service.OrderManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class OrderController {

    private OrderManager orderManager;
    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PostMapping(path="/order")
    public @ResponseBody String newOrder (@RequestBody Map<String, Object> order){
        return orderManager.addNewOrder(order);
    }

    @GetMapping(path="/pay-method")
    public @ResponseBody Iterable<PayMethod> getPayMethod(){
        return orderManager.getPayMethods();
    }

    @GetMapping(path="/delivery-city")
    public @ResponseBody Iterable<DeliveryCities> getDeliveryCities(){
        return orderManager.getDeliveryCities();
    }

    @GetMapping(path="/order-prep/")
    public @ResponseBody Iterable<Pizza> getPrepOrder(@RequestParam(name="session_string", required = true) String session_string){
        return orderManager.getPreparedPizzas();
    }

    @PostMapping(path="/order-prep/")
    public @ResponseBody String setPizzaPrepared(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="ordered_pizza_id", required = true) Integer ordered_pizza_id){
        return orderManager.pizzaPrepared(ordered_pizza_id);
    }

    @GetMapping(path="/order-delivery/")
    public @ResponseBody Iterable<Order> getReadyOrders(@RequestParam(name="session_string", required = true) String session_string){
        return orderManager.getReadyOrders();
    }

}
