package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.controller.OrderController;
import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import hu.onlinepizzeria.server.core.service.SchedulingManagerInterface;
import hu.onlinepizzeria.server.dao.SchedulingRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class SchedulingManager implements SchedulingManagerInterface {

    @Autowired
    private SchedulingRepo schedulingRepo;

    @Autowired
    private OrderManager orderManager;

    public SchedulingManager(SchedulingRepo repository) {
        this.schedulingRepo = repository;
    }

    @Override
    public Iterable<SchedulingAlgorithms> getAlgorithms() {
        return schedulingRepo.findAll();
    }

    @Override
    public String setActiveAlgorithm(Integer id) throws InvalidParameterException {
        if (!checkScheduling(id)){
            throw new InvalidParameterException("Invalid scheduling algorithm");
        }
        schedulingRepo.setAllAlgorithmsNonActive();
        schedulingRepo.setActiveAlgorithm(id);
        orderManager.schedule();
        return "Set scheduling to active";
    }

    public boolean checkScheduling(Integer id){
        ArrayList<Integer> algorithms = new ArrayList<Integer>(schedulingRepo.getAlgorithms());
        if (algorithms.contains(id)){
            return true;
        }
        return false;
    }
}
