package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;

import javax.persistence.*;
import java.io.*;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Set;

@Entity
@Table(name = "pizza")
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(Views.Public.class)
    private Integer id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private Integer price;

    @JsonView(Views.Internal.class)
    private String picture_path;

    @JsonView(Views.Public.class)
    private Integer discount_percent;

    @JsonView(Views.Public.class)
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

    @JsonView(Views.Internal.class)
    @JsonProperty("picture")
    public String getPicture_path() throws IOException {
        return encoder(Paths.get(System.getProperty("user.dir")).getParent().resolve(picture_path).toString());
    }

    @JsonIgnore
    public String getRealPicPath(){
        return picture_path;
    }

    public void setPicture_path(String picture_path) throws InvalidParameterException {
        File pic = new File(Paths.get(System.getProperty("user.dir")).getParent().resolve(picture_path).toString());
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

    @JsonView(Views.Public.class)
    @ManyToMany
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients;

    @JsonIgnore
    @OneToMany(mappedBy = "pizza")
    private Set<OrderedPizza> oPizza;

    public static String encoder(String imagePath) throws FileNotFoundException, IOException {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        }
        return base64Image;
    }

}
