package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //TODO join with user stuff
    private Integer customer_id;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<OrderedPizza> oPizza;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name="city", nullable=false)
    private DeliveryCities city;

    private String street;

    private Integer house_number;

    private String other;

    private String comment;

    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name="pay_method", nullable=false)
    private PayMethod pay_method;

    private Timestamp deadline;

    private Integer state;

    private Timestamp delivered;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public DeliveryCities getCity() {
        return city;
    }

    public void setCity(DeliveryCities city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouse_number() {
        return house_number;
    }

    public void setHouse_number(Integer house_number) {
        this.house_number = house_number;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PayMethod getPay_method() {
        return pay_method;
    }

    public void setPay_method(PayMethod pay_method) {
        this.pay_method = pay_method;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getDelivered() {
        return delivered;
    }

    public void setDelivered(Timestamp delivered) {
        this.delivered = delivered;
    }

    public Set<OrderedPizza> getoPizza() {
        return oPizza;
    }

    public void setoPizza(Set<OrderedPizza> oPizza) {
        this.oPizza = oPizza;
    }

    public Order(Integer customer_id, DeliveryCities city, String street, Integer house_number, String other, String comment, PayMethod pay_method, Timestamp deadline, Integer state, Timestamp delivered) {
        this();
        this.customer_id = customer_id;
        this.city = city;
        this.street = street;
        this.house_number = house_number;
        this.other = other;
        this.comment = comment;
        this.pay_method = pay_method;
        this.deadline = deadline;
        this.state = state;
        this.delivered = delivered;
    }

    public Order() {
    }
}
