package hu.onlinepizzeria.server.controller;

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
public class SchedulingController {

    private SchedulingManager schedulingManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public SchedulingController(SchedulingManager schedulingManager) {
        this.schedulingManager = schedulingManager;
    }

    @GetMapping(path="/scheduling/")
    public @ResponseBody ResponseEntity getSchedulingAlgorithms(@RequestParam(name="session_string", required = true) String session_string){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) { //TODO: manager role
                return new ResponseEntity(schedulingManager.getAlgorithms(), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path="/scheduling/")
    public @ResponseBody ResponseEntity setActiveSchedulingAlgorithm(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="scheduling_id", required = true) Integer scheduling_id){
        try {
            if (jwtTokenProvider.isAdmin(session_string)) { //TODO: manager role
                return new ResponseEntity(schedulingManager.setActiveAlgorithm(scheduling_id), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (InvalidParameterException ipe){
            return new ResponseEntity(ipe.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
