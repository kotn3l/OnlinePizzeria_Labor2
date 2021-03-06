package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView(Views.Public.class)
    @Column(name = "id")
    private Integer id;

    //private Integer customer_id;
    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonView(Views.Public.class)
    private Customer customer;

    @JsonIgnore
    @JsonProperty("ordered_pizza")
    @OneToMany(mappedBy = "order")
    private Set<OrderedPizza> oPizza;

    //@JsonBackReference
    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name="city", nullable=false)
    private DeliveryCities city;

    @JsonView(Views.Public.class)
    private String street;

    @JsonView(Views.Public.class)
    private Integer house_number;

    @JsonView(Views.Public.class)
    private String other;

    @JsonView(Views.Public.class)
    private String comment;

    //@JsonBackReference
    @JsonView(Views.Public.class)
    @ManyToOne
    @JoinColumn(name="pay_method", nullable=false)
    private PayMethod pay_method;

    @JsonView(Views.Public.class)
    private Timestamp deadline;

    @JsonView(Views.Public.class)
    private Integer state;

    @JsonView(Views.Public.class)
    private Timestamp delivered;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public void setComment(String comment) throws InvalidParameterException {
        if(comment.length() <= 100) {
            this.comment = comment;
        } else throw new InvalidParameterException("Comment is too long, 100 chars max");
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

    public Order(Customer customer, DeliveryCities city, String street, Integer house_number, String other, String comment, PayMethod pay_method, Timestamp deadline, Integer state, Timestamp delivered) {
        this();
        this.customer = customer;
        this.city = city;
        this.street = street;
        this.house_number = house_number;
        this.other = other;
        setComment(comment);
        this.pay_method = pay_method;
        this.deadline = deadline;
        this.state = state;
        this.delivered = delivered;
    }

    public Order() {
    }

    public static class OrderBuilder{
        private Integer id;
        private Integer customer;
        private Integer city;
        private String street;
        private Integer house_number;
        private String other;
        private String comment;
        private Integer pay_method;
        private Timestamp deadline;
        private Integer state;
        private Timestamp delivered;

        public OrderBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public OrderBuilder setCustomer(Integer customer) {
            this.customer = customer;
            return this;
        }

        public OrderBuilder setCity(Integer city) {
            this.city = city;
            return this;
        }

        public OrderBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public OrderBuilder setHouse_number(Integer house_number) {
            this.house_number = house_number;
            return this;
        }

        public OrderBuilder setOther(String other) {
            this.other = other;
            return this;
        }

        public OrderBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public OrderBuilder setPay_method(Integer pay_method) {
            this.pay_method = pay_method;
            return this;
        }

        public OrderBuilder setDeadline(Timestamp deadline) {
            this.deadline = deadline;
            return this;
        }

        public OrderBuilder setState(Integer state) {
            this.state = state;
            return this;
        }

        public OrderBuilder setDelivered(Timestamp delivered) {
            this.delivered = delivered;
            return this;
        }

        public OrderBuilder(Integer customer, Integer city, String street, Integer house_number, String other, String comment, Integer pay_method, Timestamp deadline, Integer state, Timestamp delivered) {
            this.customer = customer;
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

        public Order build() {
            return new Order(new Customer(this.customer), new DeliveryCities(this.city), this.street, this.house_number, this.other, this.comment, new PayMethod(this.pay_method), this.deadline, this.state, this.delivered);
        }
    }
}
