package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
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

    @JsonView(Views.Public.class)
    @PostMapping(path="/order")
    public @ResponseBody ResponseEntity newOrder (@RequestBody Map<String, Object> order){
        try {
            orderManager.addNewOrder(order);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (InvalidParameterException ipe){
            return new ResponseEntity(ipe.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/pay-method")
    public @ResponseBody ResponseEntity getPayMethod(){
        try {
            return new ResponseEntity(orderManager.getPayMethods(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/delivery-city")
    public @ResponseBody ResponseEntity getDeliveryCities(){
        try {
            return new ResponseEntity(orderManager.getDeliveryCities(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/order-prep/")
    public @ResponseBody ResponseEntity getPrepOrder(@RequestParam(name="session_string", required = true) String session_string){
        try {
            if (jwtTokenProvider.isKitchen(session_string)) {
                return new ResponseEntity(orderManager.getPreparedPizzas(), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.Public.class)
    @PostMapping(path="/order-prep/")
    public @ResponseBody ResponseEntity setPizzaPrepared(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="orderPizza", required = true) Integer orderPizza){
        try {
            if (jwtTokenProvider.isKitchen(session_string)) {
                orderManager.pizzaPrepared(orderPizza);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/order-delivery/")
    public @ResponseBody ResponseEntity getReadyOrders(@RequestParam(name="session_string", required = true) String session_string){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) {
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
