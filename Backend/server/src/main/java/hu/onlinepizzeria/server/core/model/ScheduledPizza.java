package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;

import java.io.Serializable;
import java.util.ArrayList;

public class ScheduledPizza implements Serializable {
    @JsonView(Views.Public.class)
    private Integer orderPizza;

    @JsonView(Views.Public.class)
    private Integer prepNum;

    @JsonView(Views.Public.class)
    private Pizza pizza;

    public Integer getOrderPizza() {
        return orderPizza;
    }

    public void setOrderPizza(Integer orderPizza) {
        this.orderPizza = orderPizza;
    }

    public Integer getPrepNum() {
        return prepNum;
    }

    public void setPrepNum(Integer prepNum) {
        this.prepNum = prepNum;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public ScheduledPizza(Integer orderPizza, Integer prepNum, Pizza pizza) {
        this.orderPizza = orderPizza;
        this.prepNum = prepNum;
        this.pizza = pizza;
    }
}
