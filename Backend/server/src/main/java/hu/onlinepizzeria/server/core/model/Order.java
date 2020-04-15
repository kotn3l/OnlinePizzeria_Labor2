package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Integer customer_id;

    @OneToOne
    @JoinColumn(name = "city", referencedColumnName = "id")
    private Integer city;

    private String street;

    private Integer house_number;

    private String other;

    private String comment;

    @OneToOne
    @JoinColumn(name = "pay_method", referencedColumnName = "id")
    private Integer pay_method;

    private Timestamp deadline;

    private Integer state;

    private Timestamp delivered;
}
