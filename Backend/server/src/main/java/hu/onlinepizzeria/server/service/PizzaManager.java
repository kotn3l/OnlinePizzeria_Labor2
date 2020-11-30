package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.exceptions.InvalidData;
import hu.onlinepizzeria.server.core.exceptions.InvalidId;
import hu.onlinepizzeria.server.core.model.Ingredient;
import hu.onlinepizzeria.server.core.model.Pizza;
import hu.onlinepizzeria.server.core.service.PizzaManagerInterface;
import hu.onlinepizzeria.server.dao.IngredientRepo;
import hu.onlinepizzeria.server.dao.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

public class PizzaManager implements PizzaManagerInterface {

    @Autowired
    private PizzaRepo pizzaRepo;

    @Autowired
    private IngredientRepo ingredientRepo;

    public PizzaManager(PizzaRepo repository, IngredientRepo ingredientRepo) {

        this.pizzaRepo = repository;
        this.ingredientRepo = ingredientRepo;
    }

    private static Path serverPath = Paths.get(System.getProperty("user.dir"));
    private static Path imagePath = Paths.get("images\\pizza");

    @Override
    public String addNewPizza (Map<String, Object> pizza, MultipartFile multipart) throws IOException, InvalidData {
        ArrayList<String> pIngredients = (ArrayList<String>)pizza.get("ingredients");
        if(pIngredients.size() <= 1) {
            throw new InvalidData("Egy pizzának több, mint egy hozzávalónak kell lennie!");
        }
        ArrayList<String> pizzaIngredients = new ArrayList<>(pIngredients);
        Path filePath = write(multipart, serverPath, imagePath);
        Pizza p = new Pizza();
        p.setName(pizza.get("name").toString());
        p.setPicture_path(filePath.toString());
        p.setPrice(Integer.parseInt(pizza.get("price").toString()));
        p.setDiscount_percent(Integer.parseInt(pizza.getOrDefault("discount_percent",0).toString()));
        p.setUnavailable(false);
        pizzaRepo.save(p);

        ingredientCheck(pIngredients);
        saveIngredients(pIngredients);
        savePizzaIngredients(p.getId(), pizzaIngredients, false);

        return "Saved";
    }

    @Override
    public Iterable<Pizza> getAllPizzas() {
        return pizzaRepo.getVisiblePizza();
    }

    @Override
    public Iterable<Pizza> getDiscountedPizzas() {
        return pizzaRepo.getDiscountedPizza();
    }

    @Override
    public String updatePizza(Integer id, Map<String, Object> pizza, MultipartFile multipart) throws IOException, InvalidData, InvalidId {
        ArrayList<String> pIngredients = (ArrayList<String>)pizza.get("ingredients");
        Path filePath = null;
        if (multipart != null){
            filePath = write(multipart, serverPath, imagePath);
        }
        if (!pizzaRepo.existsById(id)){
            throw new InvalidId("pizza", id.toString());
        }
        Pizza p = new Pizza();
        p.setId(id);
        p.setName(pizza.get("name").toString());
        if (filePath == null){
            Pizza oldPizza = pizzaRepo.getPizzaById(id);
            p.setPicture_path(oldPizza.getRealPicPath());
        } else p.setPicture_path(filePath.toString());
        p.setPrice(Integer.parseInt(pizza.get("price").toString()));
        p.setDiscount_percent(Integer.parseInt(pizza.get("discount_percent").toString()));
        p.setUnavailable(false);
        pizzaRepo.updatePizza(id, p.getName(), p.getPrice(), p.getRealPicPath(), p.getDiscount_percent(), p.isUnavailable());

        if (pIngredients != null){
            if(pIngredients.size() <= 1) {
                throw new InvalidData("Egy pizzának több, mint egy hozzávalónak kell lennie!");
            }
            ArrayList<String> pizzaIngredients = new ArrayList<>(pIngredients);
            ingredientCheck(pIngredients);
            saveIngredients(pIngredients);
            savePizzaIngredients(p.getId(), pizzaIngredients, true);
        }
        return "Pizza updated";
    }

    @Override
    public String deletePizza(Integer id) throws InvalidId {
        Pizza p = pizzaRepo.findById(id).orElse(null);
        if (p != null) {
            pizzaRepo.updatePizza(id, p.getName(), p.getPrice(), p.getRealPicPath(), p.getDiscount_percent(), true);
        }
        else{
            throw new InvalidId("pizza",id.toString());
        }
        return "Pizza deleted";
    }

    public ArrayList<String> ingredientCheck(ArrayList<String> pizzaIngredients){
        ArrayList<Ingredient> currentIngredients = ingredientRepo.getIngredients();
        ArrayList<String> temp = pizzaIngredients;
        if (currentIngredients.size() > 0) {
            for (Ingredient ci : currentIngredients
            ) {
                if (temp.contains(ci.getName())) {
                    temp.remove(ci.getName());
                }

            }
        }
        return temp;
    }

    public void saveIngredients(ArrayList<String> pizzaIngredients){
        for (String s: pizzaIngredients
             ) {
            ingredientRepo.addIngredient(s);
        }
    }

    public void savePizzaIngredients(Integer pizzaId, ArrayList<String> pizzaIngredients, boolean update){
        if (update){
            ingredientRepo.deletePizzaIngredients(pizzaId);
        }
        ArrayList<Ingredient> currentIngredients = ingredientRepo.getIngredients();
        for (Ingredient ci : currentIngredients
        ) {
            if (pizzaIngredients.contains(ci.getName())) {
                ingredientRepo.addIngredientAndPizza(pizzaId, ci.getId());
            }
        }
    }

    public Path write(MultipartFile file, Path sysDir, Path imgDir) throws IOException {
        Path tempPath = serverPath.getParent().resolve(imagePath);
        if (!Files.exists(tempPath)){
            File temp = new File(tempPath.toUri());
            temp.mkdirs();
        }
        Path filepath = Paths.get(tempPath.toString(), file.getOriginalFilename());
        Path toSave = Paths.get(imgDir.toString(), file.getOriginalFilename());
        if (Files.exists(filepath)){
            Files.delete(filepath);
        }
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
            return toSave;
        }
    }
}
