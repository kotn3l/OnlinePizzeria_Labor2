package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;

@Entity
@Table(name = "delivery_cities")
public class DeliveryCities {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;
}
