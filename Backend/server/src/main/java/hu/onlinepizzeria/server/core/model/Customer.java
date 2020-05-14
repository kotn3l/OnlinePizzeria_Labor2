package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonView(Views.Public.class)
    @Column(name = "id")
    private Integer id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String email;

    @JsonView(Views.Public.class)
    private String telephone;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidParameterException {
        if (isValidEmail(email)) {
            this.email = email;
        }
        else throw new InvalidParameterException("E-mail is invalid");
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws InvalidParameterException {
        if (isValidTelephone(telephone)) {
            this.telephone = telephone;
        }
        else throw new InvalidParameterException("Telephone is invalid");
    }

    public Customer() {

    }

    public Customer(String name, String email, String telephone) {
        this();
        this.name = name;
        setEmail(email);
        setTelephone(telephone);
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    static boolean isValidTelephone(String telephone) {
        if (telephone.charAt(0) == '+') {
            String regex = "\\+\\d{11}";
            return telephone.matches(regex);
        } else {
            String regex = "\\d{11}";
            return telephone.matches(regex);
        }
    }
}
