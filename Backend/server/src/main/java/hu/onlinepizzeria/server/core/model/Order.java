package hu.onlinepizzeria.server.core.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JoinColumn(name = "id")
    private Integer id;

    //@OneToOne
    //@JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Integer customer_id;

    @OneToOne(targetEntity = DeliveryCities.class)
    @JoinColumn(name = "city", referencedColumnName = "id")
    private Integer city;

    private String street;

    private Integer house_number;

    private String other;

    private String comment;

    @OneToOne(targetEntity = PayMethod.class)
    @JoinColumn(name = "pay_method", referencedColumnName = "id")
    private Integer pay_method;

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

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
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

    public Integer getPay_method() {
        return pay_method;
    }

    public void setPay_method(Integer pay_method) {
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

    public Order(Integer customer_id, Integer city, String street, Integer house_number, String other, String comment, Integer pay_method, Timestamp deadline, Integer state, Timestamp delivered) {
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
}
