package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import hu.onlinepizzeria.server.core.service.SchedulingManagerInterface;
import hu.onlinepizzeria.server.dao.SchedulingRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class SchedulingManager implements SchedulingManagerInterface {

    @Autowired
    private SchedulingRepo schedulingRepo;

    public SchedulingManager(SchedulingRepo repository) {
        this.schedulingRepo = repository;
    }

    @Override
    public Iterable<SchedulingAlgorithms> getAlgorithms() {
        return schedulingRepo.findAll();
    }

    @Override
    public String setActiveAlgorithm(Integer id) {
        schedulingRepo.setAllAlgorithmsNonActive();
        schedulingRepo.setActiveAlgorithm(id);
        //schedulingRepo.setOtherAlgorithmsNonActive(id);
        return "Set scheduling to active";
    }
}
