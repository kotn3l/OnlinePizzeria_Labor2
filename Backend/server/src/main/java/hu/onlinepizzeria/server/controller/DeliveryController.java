package hu.onlinepizzeria.server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class DeliveryController {

    //private DeliveryManager deliveryManager;
    //constructor that sets the deliveryManager prop

    @GetMapping(path="/delivery-guy/{session_string}")
    public @ResponseBody
    String getDeliveryGuys(@PathVariable String session_string){
        return "getDeliveryGuys not yet implemented";
    }

    @PostMapping(path="/delivery/{session_string}/{delivery_guy}", headers = "")
    public @ResponseBody String assignOrdersToDeliveryGuy(@PathVariable String session_string, @PathVariable Integer delivery_guy){
        return "assignOrdersToDeliveryGuy not yet implemented";
    }

    @GetMapping(path="/delivery/{session_string}/{delivery_guy}")
    public @ResponseBody
    String getDeliveriesOfAGuy(@PathVariable String session_string, @PathVariable Integer delivery_guy){
        return "getDeliveriesOfAGuy not yet implemented";
    }

    @PostMapping(path="/delivery/{session_string}/{delivery_id}")
    public @ResponseBody String setOrderDelivered(@PathVariable String session_string, @PathVariable Integer delivery_id){
        return "setOrderDelivered not yet implemented";
    }
}
