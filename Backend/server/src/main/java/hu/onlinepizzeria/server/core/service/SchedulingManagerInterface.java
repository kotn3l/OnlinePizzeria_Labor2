package hu.onlinepizzeria.server.core.service;

import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;

public interface SchedulingManagerInterface {
    public Iterable<SchedulingAlgorithms> getAlgorithms();
    public String setActiveAlgorithm(Integer id) throws InvalidId;
}
