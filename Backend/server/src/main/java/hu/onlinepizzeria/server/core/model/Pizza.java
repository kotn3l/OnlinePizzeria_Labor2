package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Set;

@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    private Integer price;

    private String picture_path;

    private Integer discount_percent;

    private boolean unavailable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) throws InvalidParameterException {
        if(price > 0){
            this.price = price;
        }
        else throw new InvalidParameterException("Price must be greater than 0");
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) throws InvalidParameterException {
        File pic = new File(picture_path);
        if(pic.isFile()) {
            this.picture_path = picture_path;
        }
        else throw new InvalidParameterException("Picture does not exist");
    }

    public Integer getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(Integer discount_percent) throws InvalidParameterException {
        if(discount_percent >= 0) {
            this.discount_percent = discount_percent;
        }
        else throw new InvalidParameterException("Discount percent can't be negative");
    }

    public boolean isUnavailable() {
        return unavailable;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients;

    @JsonIgnore
    @OneToMany(mappedBy = "pizza")
    private Set<OrderedPizza> oPizza;

}
