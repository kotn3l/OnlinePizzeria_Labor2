package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.model.SchedulingAlgorithms;
import hu.onlinepizzeria.server.core.service.SchedulingManagerInterface;
import hu.onlinepizzeria.server.dao.IngredientRepo;
import hu.onlinepizzeria.server.dao.OrderRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import hu.onlinepizzeria.server.dao.SchedulingRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SchedulingManager implements SchedulingManagerInterface {

    @Autowired
    private SchedulingRepo schedulingRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    @Autowired
    private PizzaRepo pizzaRepo;

    public SchedulingManager(SchedulingRepo repository, OrderRepo orderRepo, IngredientRepo ingredientRepo, PizzaRepo pizzaRepo) {
        this.schedulingRepo = repository;
        this.orderRepo = orderRepo;
        this.ingredientRepo = ingredientRepo;
        this.pizzaRepo = pizzaRepo;
    }

    @Override
    public Iterable<SchedulingAlgorithms> getAlgorithms() {
        return schedulingRepo.findAll();
    }

    @Override
    public String setActiveAlgorithm(Integer id) throws InvalidId {
        if (!checkScheduling(id)){
            throw new InvalidId("scheduling", id.toString());
        }
        schedulingRepo.setAllAlgorithmsNonActive();
        schedulingRepo.setActiveAlgorithm(id);
        schedule();
        return "Set scheduling to active";
    }

    public boolean checkScheduling(Integer id){
        ArrayList<Integer> algorithms = new ArrayList<Integer>(schedulingRepo.getAlgorithms());
        if (algorithms.contains(id)){
            return true;
        }
        return false;
    }

    public void schedule(){
        Integer active = schedulingRepo.getActiveAlgorithm();
        if (active == 1){
            scheduleOrderedPizzasDeadline();
        }
        else if (active == 2){
            scheduleOrderedPizzasDeadline(); //schedule with other because this one is f*cked
        } else {
            throw new InvalidParameterException("No scheduling is set to active! Please set scheduling either to 1 (order deadline oriented) or 2 (ingredient-oriented)");
        }
    }

    public void scheduleOrderedPizzasDeadline(){
        orderRepo.truncateScheduledPizzas();
        ArrayList<Integer> orderIdsInOrder = orderRepo.getOrderInOrderAsc();
        ArrayList<Integer> orderPizzaIds = new ArrayList<>();
        for (Integer oi: orderIdsInOrder
        ) {
            orderPizzaIds.addAll(orderRepo.getOrderPizzaByOId(oi));
        }
        ArrayList<Integer> orderPizzaIdsDone = orderRepo.getOrderPizzaDone();
        orderPizzaIds.removeAll(orderPizzaIdsDone);
        Integer prepNum = 1;
        for (Integer opi: orderPizzaIds
        ) {
            orderRepo.addOrderPizza(opi, prepNum);
            prepNum++;
        }
    }

    public void scheduleOrderedPizzasIngredient(){ //buggy af doesn't work
        orderRepo.truncateScheduledPizzas();
        ArrayList<Integer> notDonePizzaIds = orderRepo.notDonePizzas();
        ArrayList<Pizza> pizzas = new ArrayList<>();
        for (Integer ndpi: notDonePizzaIds
        ) {
            pizzas.add(pizzaRepo.getPizzaById(ndpi));
        }
        HashMap<Integer, Integer> ingredientDifference = new HashMap<>();
        ArrayList<Integer> difference = ingredientRepo.getIngredientIdByPizza(pizzas.get(1).getId());
        for (int j = 1; j < pizzas.size(); j++){
            difference.removeAll(ingredientRepo.getIngredientIdByPizza(pizzas.get(j).getId()));
            ingredientDifference.put(pizzas.get(j).getId(), difference.size());
        }
        ArrayList<Integer> values = new ArrayList<>(ingredientDifference.values());
        Collections.sort(values);
        ArrayList<Integer> sortedPizzas = new ArrayList<>();
        for (int i = 0; i < values.size(); i++){
            sortedPizzas.add(getKey(ingredientDifference, values.get(i)));
        }
        ArrayList<Integer> orderPizzas = new ArrayList<>();
        for (int i = 0; i < sortedPizzas.size(); i++){
            orderPizzas.addAll(orderRepo.getOrderPizzasByPizza(sortedPizzas.get(i)));
        }
        Integer prepNum = 1;
        for (Integer opi: orderPizzas
        ) {
            orderRepo.addOrderPizza(opi, prepNum);
            prepNum++;
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
}
