package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.service.OrderManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class OrderController {

    private OrderManager orderManager;
    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PostMapping(path="/order")
    public @ResponseBody
    String newOrder (@RequestParam String name, @RequestParam String email, @RequestParam String telephone, @RequestParam String city, @RequestParam String street, String houseNum, String other, String comment, String payMethod){
        return "newOrder not implemented " + " replace String at payMethod with PayMethod class if it's done";
    }

    @GetMapping(path="/pay-method")
    public @ResponseBody String getPayMethod(){
        return "getPayMethod not yet implemented";
    }

    @GetMapping(path="/delivery-city")
    public @ResponseBody String getDeliveryCities(){
        return "getDeliveryCities not yet implemented";
    }

    @GetMapping(path="/order-prep/")
    public @ResponseBody String getPrepOrder(@RequestParam(name="session_string", required = true) String session_string){
        return "getPrepOrder not yet implemented";
    }

    @PostMapping(path="/order-prep/")
    public @ResponseBody String setPizzaPrepared(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="ordered_pizza_id", required = true) Integer ordered_pizza_id){
        return "setPizzaPrepared not yet implemented";
    }

    @GetMapping(path="/order-delivery/")
    public @ResponseBody String getReadyOrders(@RequestParam(name="session_string", required = true) String session_string){
        return "getReadyOrders not yet implemented";
    }

}
