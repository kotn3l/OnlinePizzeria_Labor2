package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "pay_method")
public class PayMethod implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView(Views.Public.class)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    //@JsonManagedReference
    @OneToMany(mappedBy="pay_method")
    private Set<Order> order;

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    @JsonView(Views.Public.class)
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

    public PayMethod(Integer id) {
        this();
        this.id = id;
    }

    public PayMethod() {

    }
}
