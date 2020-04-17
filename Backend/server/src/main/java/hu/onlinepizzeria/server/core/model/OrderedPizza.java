package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_pizza")
public class OrderedPizza implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Integer order_id;

    @OneToOne(targetEntity = Pizza.class)
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Integer pizza_id;

    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getPizza_id() {
        return pizza_id;
    }

    public void setPizza_id(Integer pizza_id) {
        this.pizza_id = pizza_id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
