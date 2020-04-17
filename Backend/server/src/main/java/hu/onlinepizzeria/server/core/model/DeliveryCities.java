package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "delivery_cities")
public class DeliveryCities implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JoinColumn(name = "id")
    //@OneToOne(cascade = CascadeType.ALL, mappedBy = "Order")
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
