package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.service.OrderManager;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private OrderManager orderManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public OrderController(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    @PostMapping(path="/order")
    public @ResponseBody ResponseEntity newOrder (@RequestBody Map<String, Object> order){
        try {
            return new ResponseEntity(orderManager.addNewOrder(order), HttpStatus.OK);
        }
        catch (InvalidParameterException ipe){
            return new ResponseEntity(ipe.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/pay-method")
    public @ResponseBody ResponseEntity getPayMethod(){
        try {
            return new ResponseEntity(orderManager.getPayMethods(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/delivery-city")
    public @ResponseBody ResponseEntity getDeliveryCities(){
        try {
            return new ResponseEntity(orderManager.getDeliveryCities(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/order-prep/")
    public @ResponseBody ResponseEntity getPrepOrder(@RequestParam(name="session_string", required = true) String session_string){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) { //TODO: kitchen staff role
                return new ResponseEntity(orderManager.getPreparedPizzas(), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/order-prep/")
    public @ResponseBody ResponseEntity setPizzaPrepared(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="ordered_pizza_id", required = true) Integer ordered_pizza_id){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) { //TODO: kitchen staff role
                return new ResponseEntity(orderManager.pizzaPrepared(ordered_pizza_id), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/order-delivery/")
    public @ResponseBody ResponseEntity getReadyOrders(@RequestParam(name="session_string", required = true) String session_string){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) { //TODO: moderator role
                return new ResponseEntity(orderManager.getReadyOrders(), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
