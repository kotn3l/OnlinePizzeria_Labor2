package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.exceptions.UnauthorizedEx;
import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import hu.onlinepizzeria.server.service.SchedulingManager;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SchedulingController {

    private SchedulingManager schedulingManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public SchedulingController(SchedulingManager schedulingManager) {
        this.schedulingManager = schedulingManager;
    }

    @JsonView(Views.Public.class)
    @GetMapping(path="/scheduling/")
    public @ResponseBody ResponseEntity getSchedulingAlgorithms(@RequestParam(name="session_string", required = true) String session_string) throws UnauthorizedEx {
        if (jwtTokenProvider.isManager(session_string)) {
            return new ResponseEntity(schedulingManager.getAlgorithms(), HttpStatus.OK);
        } else {
            throw new UnauthorizedEx("Ehhez manager jogok szükségesek!");
        }
    }

    @JsonView(Views.Public.class)
    @PostMapping(path="/scheduling/")
    public @ResponseBody ResponseEntity setActiveSchedulingAlgorithm(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="scheduling_id", required = true) Integer scheduling_id) throws InvalidId, UnauthorizedEx {
        if (jwtTokenProvider.isManager(session_string)) {
            schedulingManager.setActiveAlgorithm(scheduling_id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new UnauthorizedEx("Ehhez manager jogok szükségesek!");
        }
    }
}
