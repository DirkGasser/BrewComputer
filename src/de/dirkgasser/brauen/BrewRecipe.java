/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dirk
 */
public class BrewRecipe {
    String recipeName;
    List<BrewStep> brewSteps = new ArrayList<>();
    
    public BrewRecipe (String recipeName, List<BrewStep> brewSteps) {
        this.recipeName = recipeName;
        this.brewSteps = brewSteps;
    }
    
    public BrewRecipe (String recipeName, BrewStep brewStep) {
        this.recipeName = recipeName;
        this.brewSteps.add(brewStep);
    }
    
    public BrewRecipe (String recipeName) {
        this.recipeName = recipeName;
    }
    
    public String getRecipeName(){
        return recipeName;
    }
    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }
    public Integer getNumberOfSteps() {
        return brewSteps.size();
    }
    public static BrewRecipe getBrewRecipeFromFile (String recipeName) {
        try {
            File recFile = new File(System.getProperty("user.home") + File.separator + recipeName + ".brc");
            if (recFile.exists() && recFile.isFile()) { 
                Reader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + File.separator + recipeName + ".brc"));
                Gson gson = new GsonBuilder().create();
                BrewRecipe recipe = gson.fromJson(reader, BrewRecipe.class);
                return recipe;
            } else { 
                return new BrewRecipe (recipeName);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new BrewRecipe (recipeName);
    }
    
    public List<BrewStep> getbrewSteps() {
        return brewSteps;
    }
    
    public BrewStep getBrewStepbyPosition(Integer position) {
        Integer bcounter = 0;
        for (BrewStep brewStep : brewSteps) {
            bcounter = bcounter + 1;
            if (bcounter == position) {
               return brewStep;
            }   
        }
        return null; 
    }
    public Boolean deleteBrewStep(Integer position) {
        BrewStep tobedeletedStep = this.getBrewStepbyPosition(position);
        if (tobedeletedStep.hasIngredients()) {
            return false;
        } else if (brewSteps.size() == 1) {
            return false;
        } else{
            brewSteps.remove(tobedeletedStep);
            return true;
        }
    }
    
    public List<String> AllIngredients () {
        List<String> ingreds = new ArrayList<>();
        StringBuilder oneIngred = new StringBuilder(); 
        for (BrewStep brewStep : brewSteps) {
            if (brewStep.hasIngredients()) {
                for (TypeofIngredient typ : TypeofIngredient.values()) {
                    for (Ingredient ingredient : brewStep.getIngredients(typ)) {
                        oneIngred.setLength(0);
                        oneIngred.append(ingredient.getDescripton());
                        oneIngred.append("  ");
                        oneIngred.append(ingredient.getIngredTypeF());
                        if (ingredient.getAmount() > 0) {
                           oneIngred.append("  ");
                           oneIngred.append(ingredient.getAmountF()); 
                           oneIngred.append(" ");
                           oneIngred.append(ingredient.getUnit()); 
                        }
                        if (!ingredient.getProperty().equals(PropertyofIndredient.NONE)) {
                           oneIngred.append("  ");
                           oneIngred.append(ingredient.getPropertyF()); 
                           oneIngred.append(" ");
                           oneIngred.append(ingredient.getPropertyValueF()); 
                        }
                        ingreds.add(oneIngred.toString());
                    }
                 }
            }
        }
        return ingreds;
    }
    
    public List<Ingredient> getIngredients () {
        List<Ingredient> ingreds = new ArrayList<>();
        for (BrewStep brewStep : brewSteps) {
            if (brewStep.hasIngredients()) {
                for (TypeofIngredient typ : TypeofIngredient.values()) {
                    for (Ingredient ingredient : brewStep.getIngredients(typ)) {
                        ingreds.add(ingredient);
                    }
                 }
            }
        }
        return ingreds;
    }
    
    public Integer getIngredBrewstep (Integer listPosition) {
        Integer bcounter = 0;
        Integer icounter = 0;
        for (BrewStep brewStep : brewSteps) {
            bcounter = bcounter + 1;
            for (TypeofIngredient typ : TypeofIngredient.values()) {
                for (Ingredient ingredient : brewStep.getIngredients(typ)) {
                    icounter = icounter + 1;
                    if (icounter == listPosition) {
 // to be used in combox, index starts at 0 for combobox
                        return bcounter - 1;
                    }
                }
            }   
        }
        return 0;
    }
    
    public Ingredient getIngredbyPosition (Integer listPosition) {
        Integer bcounter = 0;
        Integer icounter = 0;
        for (BrewStep brewStep : brewSteps) {
            bcounter = bcounter + 1;
            for (TypeofIngredient typ : TypeofIngredient.values()) {
                for (Ingredient ingredient : brewStep.getIngredients(typ)) {
                    icounter = icounter + 1;
                    if (icounter == listPosition) {
                        return ingredient;
                    }
                }
            }   
        }
        return null;
    }
       
    public void addBrewStep (BrewStep brewStep, Integer position) {
        if (brewSteps.size() > position) {
            brewSteps.add(position, brewStep);
        } else {
            brewSteps.add(brewStep);
        }
    } 
    
    public void transferIngred (Integer position, Integer brewStepNo) {
        Ingredient tobemovedIngred = null;
        Integer bcounter = 0;
        Integer icounter = 0;
        for (BrewStep brewStep : brewSteps) {
            bcounter = bcounter + 1;
            for (TypeofIngredient typ : TypeofIngredient.values()) {
                for (Ingredient ingredient : brewStep.getIngredients(typ)) {
                    icounter = icounter + 1;
                    if (icounter == position) {
                        tobemovedIngred = ingredient;
                        brewStep.deleteIngredient(ingredient);
                    }
                }
            }   
        }
        brewSteps.get(brewStepNo).addIngredient(tobemovedIngred);
    }
    
    public void writeBrewRecipe () {
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       Writer writer;
        try {
            writer = new FileWriter(System.getProperty("user.home") + File.separator + this.recipeName + ".brc");
            gson.toJson(this, writer);  
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(BrewRecipe.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
}
