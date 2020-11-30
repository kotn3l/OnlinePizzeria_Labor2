package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hu.onlinepizzeria.server.core.Views;
import hu.onlinepizzeria.server.core.exceptions.InvalidData;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.exceptions.UnauthorizedEx;
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
@CrossOrigin(origins = "http://localhost:4200")
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

    @JsonView(Views.Public.class)
    @GetMapping(path="/delivery-guy/")
    public @ResponseBody
    ResponseEntity getDeliveryGuys(@RequestParam(name="session_string", required = true) String session_string) throws UnauthorizedEx {
        if (!jwtTokenProvider.validateToken(session_string)){
            /*Map<Object, Object> model = new HashMap<>();
            model.put("error","Invalid session string.");
            return new ResponseEntity(model, HttpStatus.UNAUTHORIZED);*/
            throw new UnauthorizedEx("Ehhez admin jogok szükségesek!");
        }
        if (jwtTokenProvider.isAdmin(session_string)) {
            return ok(deliveryManager.getAllDeliveryGuys());
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @JsonView(Views.Public.class)
    @PostMapping(path="/delivery/", consumes = "application/json")
    public ResponseEntity assignOrdersToDeliveryGuy(@RequestParam(name="session_string", required = true) String session_string,
                                                          @RequestParam(name="delivery_guy", required = true) Integer delivery_guy,
                                                          @RequestBody List<Integer> orders) throws InvalidData, InvalidId, UnauthorizedEx {
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isAdmin(session_string)) {
                deliveryManager.addNewDelivery(delivery_guy, orders);
                return new ResponseEntity(HttpStatus.CREATED);
                //return new ResponseEntity(objectNode,HttpStatus.BAD_REQUEST);
            }
            else {
                objectNode.put("error", "Requires a higher access rigth to perform this request.");
                throw new UnauthorizedEx("Ehhez admin jogok szükségesek!");
            }
        }
        else {
            objectNode.put("error", "Invalid session string.");
            throw new UnauthorizedEx("Invalid session string");
        }
        //return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/delivery/")
    public ResponseEntity
    getDeliveriesOfAGuy(@RequestParam(name="session_string", required = true) String session_string,
                        @RequestParam(name="delivery_guy", required = false) Integer delivery_guy) throws UnauthorizedEx {
        //getUsername()
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isDelivery(session_string)) {
                List<ObjectNode> result = deliveryManager.getDeliveriesByDeliveryGuy(userRepo.findUserByEmail(jwtTokenProvider.getUsername(session_string)).get().getId());

                return new ResponseEntity(result, HttpStatus.OK);
            }
            else{
                objectNode.put("error", "Define the delivery guy.");
                throw new UnauthorizedEx("Ehhez delivery jogok szükségesek!");
            }
        }
        else{
            objectNode.put("error", "Invalid session string.");
            throw new UnauthorizedEx("Invalid session string");
        }
        //return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }

    @JsonView(Views.Public.class)
    @PostMapping(path="/delivery-done/")
    public ResponseEntity setOrderDelivered(@RequestParam(name="session_string", required = true) String session_string,
                                            @RequestParam(name="delivery_id", required = true) Integer delivery_id) throws InvalidId, UnauthorizedEx {
        ObjectNode objectNode = mapper.createObjectNode();
        if (jwtTokenProvider.validateToken(session_string)) {
            if (jwtTokenProvider.isDelivery(session_string)) {
                deliveryManager.updateDelivery(delivery_id);
                return new ResponseEntity(HttpStatus.OK);
                //return new ResponseEntity(objectNode, HttpStatus.BAD_REQUEST);
            }
            else{
                objectNode.put("error", "Not delivery");
                throw new UnauthorizedEx("Ehhez delivery jogok szükségesek!");
            }
        }
        else{
            objectNode.put("error", "Invalid session string.");
            throw new UnauthorizedEx("Invalid session string");
        }
        //return new ResponseEntity(objectNode, HttpStatus.UNAUTHORIZED);
    }
}
