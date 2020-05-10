package hu.onlinepizzeria.server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryController {

    //private DeliveryManager deliveryManager;
    //constructor that sets the deliveryManager prop

    @GetMapping(path="/delivery-guy/")
    public @ResponseBody
    String getDeliveryGuys(@RequestParam(name="session_string", required = true) String session_string){
        return "getDeliveryGuys not yet implemented";
    }

    @PostMapping(path="/delivery/", headers = "")
    public @ResponseBody String assignOrdersToDeliveryGuy(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="delivery_guy", required = true) Integer delivery_guy){
        return "assignOrdersToDeliveryGuy not yet implemented";
    }

    @GetMapping(path="/delivery/")
    public @ResponseBody
    String getDeliveriesOfAGuy(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="delivery_guy", required = true) Integer delivery_guy){
        return "getDeliveriesOfAGuy not yet implemented";
    }

    @PostMapping(path="/delivery/")
    public @ResponseBody String setOrderDelivered(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="delivery_id", required = true) Integer delivery_id){
        return "setOrderDelivered not yet implemented";
    }
}
