/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dirk
 */
public class BrewStep {
    public String description;
    List<Ingredient> ingredients = new ArrayList<>();
    public Integer duration;
    public Boolean manual;
    public Integer temperatur;
    public BrewStep (String description, List<Ingredient> ingredients, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.ingredients = ingredients;
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }

    public Boolean getManual() {
        return manual;
    }

    public Integer getTemperatur() {
        return temperatur;
    }
    
    public BrewStep (String description, Ingredient ingredients, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.ingredients.add(ingredients);
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 
    
    public BrewStep (String description, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 
     public BrewStep (String description, Integer duration, Boolean manual) {
        this.description = description;
        this.duration = duration;
        this.manual = manual;
    } 
    
    public List<Ingredient> getIngredients () {
        return ingredients;
    }
    public List<Ingredient> getIngredients (TypeofIngredient typ) {
        List<Ingredient> ingred = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (typ.equals(ingredient.ingredType)) {
                ingred.add(ingredient);
            }
        }
        return ingred;
    }
    public String listIngredients() {
        StringBuilder sbList = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
               sbList.append(ingredient.getIngredTypeF());
               sbList.append(": ");
               sbList.append(ingredient.getDescripton());
               sbList.append("\n");
               sbList.append(ingredient.getAmountF());
               sbList.append(" ");
               sbList.append(ingredient.getUnit());  
               sbList.append("\n");
               sbList.append("\n");
            }
        return sbList.toString();
    }
        
    
    public Boolean hasIngredients() {
        return (!ingredients.isEmpty());
    }
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setManual(Boolean manual) {
        this.manual = manual;
    }

    public void setTemperatur(Integer temperatur) {
        this.temperatur = temperatur;
    }
}
