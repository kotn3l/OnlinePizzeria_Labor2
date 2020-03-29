package hu.onlinepizzeria.server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class OrderController {

    //private OrderManager orderManager;
    //constructor that sets the orderManager prop

    @PostMapping(path="/order")
    public @ResponseBody
    String newOrder (@RequestParam String name, @RequestParam String email, @RequestParam String telephone, @RequestParam String city, @RequestParam String street, String houseNum, String other, String comment, String payMethod){
        return "newOrder not implemented " + " replace String at payMethod with PayMethod class if it's done";
    }

    @GetMapping(path="/pay-method")
    public @ResponseBody String getPayMethod(){
        return "getPayMethod not yet implemented";
    }

    @GetMapping(path="/order-prep/{session_string}")
    public @ResponseBody String getPrepOrder(@PathVariable String session_string){
        return "getPrepOrder not yet implemented";
    }

    @PostMapping(path="/order-prep/{session_string}")
    public @ResponseBody String setPizzaPrepared(@PathVariable String session_string){
        return "setPizzaPrepared not yet implemented";
    }

    @GetMapping(path="/order-delivery/{session_string}")
    public @ResponseBody String getReadyOrders(@PathVariable String session_string){
        return "getReadyOrders not yet implemented";
    }

}
