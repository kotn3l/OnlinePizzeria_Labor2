package hu.onlinepizzeria.server.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class SchedulingController {

    //private SchedulingManager schedulingManager;
    //constructor that sets the schedulingManager prop

    @GetMapping(path="/scheduling/{session_string}")
    public @ResponseBody
    String getSchedulingAlgorithms(@PathVariable String session_string){
        return "getSchedulingAlgorithms not yet implemented";
    }

    @PostMapping(path="/scheduling/{session_string}/{scheduling_id}")
    public @ResponseBody String setActiveSchedulingAlgorithm(@PathVariable String session_string, @PathVariable Integer scheduling_id){
        return "setActiveSchedulingAlgorithm not yet implemented";
    }
}
