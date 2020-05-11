package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "delivery_man_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery implements Serializable {
    // Deliver_id + List of orders?
    // join table deliver_oder

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "delivery_man")
    private User delivery_man;

    @Column(name = "turn")
    private Integer turn;

    public Delivery(Order order, User user, Integer turn) {
        this.order_id = order;
        this.delivery_man = user;
        this.turn = turn;
    }
}
