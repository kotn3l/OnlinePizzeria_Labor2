package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import hu.onlinepizzeria.server.service.SchedulingManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class SchedulingController {

    private SchedulingManager schedulingManager;

    public SchedulingController(SchedulingManager schedulingManager) {
        this.schedulingManager = schedulingManager;
    }

    @GetMapping(path="/scheduling/")
    public @ResponseBody
    Iterable<SchedulingAlgorithms> getSchedulingAlgorithms(@RequestParam(name="session_string", required = true) String session_string){
        return schedulingManager.getAlgorithms();
    }

    @PostMapping(path="/scheduling/")
    public @ResponseBody String setActiveSchedulingAlgorithm(@RequestParam(name="session_string", required = true) String session_string, @RequestParam(name="scheduling_id", required = true) Integer scheduling_id){
        return schedulingManager.setActiveAlgorithm(scheduling_id);
    }
}
