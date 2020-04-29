package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_pizza")
public class OrderedPizza implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return this.order.getId();
    }


    public Integer getPizza_id() {
        return this.pizza.getId();
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public Order getOrder() {
        return order;
    }


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
