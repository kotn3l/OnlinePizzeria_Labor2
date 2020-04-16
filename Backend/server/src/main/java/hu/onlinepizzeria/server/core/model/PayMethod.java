package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;

@Entity
@Table(name = "pay_method")
public class PayMethod {
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