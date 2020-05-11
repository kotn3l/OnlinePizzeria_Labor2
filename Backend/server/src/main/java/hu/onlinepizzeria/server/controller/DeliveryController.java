package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.model.Order;
import hu.onlinepizzeria.server.dao.UserRepo;
import hu.onlinepizzeria.server.service.DeliveryManager;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path="/api")
public class DeliveryController {

    private final DeliveryManager deliveryManager;
    //constructor that sets the deliveryManager prop

    public DeliveryController(DeliveryManager deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepo userRepo;

    @GetMapping(path="/delivery-guy/")
    public @ResponseBody
    ResponseEntity getDeliveryGuys(@RequestParam(name="session_string", required = true) String session_string){
        if (!jwtTokenProvider.validateToken(session_string)){
            Map<Object, Object> model = new HashMap<>();
            model.put("error","Invalid session string.");
            return new ResponseEntity(model, HttpStatus.UNAUTHORIZED);
        }
        if (jwtTokenProvider.isAdmin(session_string)) {
            return ok(deliveryManager.getAllDeliveryGuys());
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path="/delivery/", consumes = "application/json")
    public ResponseEntity assignOrdersToDeliveryGuy(@RequestParam(name="session_string", required = true) String session_string,
                                                          @RequestParam(name="delivery_guy", required = true) Integer delivery_guy,
                                                          @RequestBody List<Integer> orders){
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isAdmin(session_string)) {
                try {
                    deliveryManager.addNewDelivery(delivery_guy, orders);
                    return new ResponseEntity(HttpStatus.CREATED);
                }
                catch (ExceptionInInitializerError e) { // TODO placeholder exception
                    ObjectNode error = mapper.createObjectNode();
                    error.put("delivery_guy", "Invalid delivery guy id.");
                    objectNode.set("error", error);
                }
                catch (Exception e){ // TODO placeholder exception
                    ObjectNode error = mapper.createObjectNode();
                    error.put("order_list", "Count of all orders pizza cant't be more than 10."+ e.toString());
                    objectNode.set("error", error);
                }
                return new ResponseEntity(objectNode,HttpStatus.BAD_REQUEST);
            }
            else objectNode.put("error", "Requires a higher access rigth to perform this request.");
        }
        else objectNode.put("error", "Invalid session string.");
        return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path="/delivery/")
    public ResponseEntity
    getDeliveriesOfAGuy(@RequestParam(name="session_string", required = true) String session_string,
                        @RequestParam(name="delivery_guy", required = true) Integer delivery_guy){
        //getUsername()
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isDelivery(session_string)) {
                try {
                    Map<Integer, Iterable<Order>> result =deliveryManager
                            .getDeliveriesByDeliveryGuy(userRepo
                                    .findUserByEmail(jwtTokenProvider
                                            .getUsername(session_string))
                                    .get().getId());
                    return new ResponseEntity(result, HttpStatus.OK);
                }
                catch (Exception e) { // TODO placeholder exception
                    e.printStackTrace();
                    ObjectNode error = mapper.createObjectNode();
                    error.put("delivery_guy", "Invalid delivery guy id.");
                    objectNode.set("error", error);
                }
                return new ResponseEntity(objectNode,HttpStatus.BAD_REQUEST);
            }
            else objectNode.put("error", "Define the delivery guy.");
        }
        else objectNode.put("error", "Invalid session string.");
        return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(path="/delivery-done/")
    public ResponseEntity setOrderDelivered(@RequestParam(name="session_string", required = true) String session_string,
                                            @RequestParam(name="delivery_id", required = true) Integer delivery_id){
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isDelivery(session_string)) {
                try {
                    deliveryManager.updateDelivery(delivery_id);
                    return new ResponseEntity(HttpStatus.OK);
                }
                catch (Exception e){
                    e.printStackTrace();
                    objectNode.put("error", "Invalid delivery id.");
                }
                return new ResponseEntity(objectNode, HttpStatus.BAD_REQUEST);
            }
            else objectNode.put("error", "Not delivery guy.");
        }
        else objectNode.put("error", "Invalid session string.");
        return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }
}
