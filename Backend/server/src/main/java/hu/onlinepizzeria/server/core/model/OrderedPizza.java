package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;

@Entity
@Table(name = "order_pizza")
public class OrderedPizza {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Integer order_id;

    @OneToOne
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Integer pizza_id;

    private boolean done;
}
