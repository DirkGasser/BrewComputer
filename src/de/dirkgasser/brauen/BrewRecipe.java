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

 * Brew recipe for beer <br>
 * A BrewRecipe contains a list of BrewSteps <br>
 * A BrewStep contains information about the step and a list of Ingredient <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class BrewRecipe {
    String recipeName;
    List<BrewStep> brewSteps = new ArrayList<>();
/**
 * 
 * @param recipeName Name of the receipe 
 * @param BrewSteps List of brewSteps
 */   
    public BrewRecipe (String recipeName, List<BrewStep> BrewSteps) {
        this.recipeName = recipeName;
        this.brewSteps = brewSteps;
    }
/**
 * 
 * @param recipeName Name of the receipe 
 * @param brewStep single BrewSteps
 */     
    public BrewRecipe (String recipeName, BrewStep brewStep) {
        this.recipeName = recipeName;
        this.brewSteps.add(brewStep);
    }
/**
 * 
 * @param recipeName Name of the receipe 
 */       
    public BrewRecipe (String recipeName) {
        this.recipeName = recipeName;
    }
/**
 * read Name of receipe 
 * @return recipeName Name of the receipe 
 */      
    public String getRecipeName(){
        return recipeName;
    }
/**
 * set Name of receipe 
 * @param recipeName Name of the receipe 
 */  
    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }
/**
 * get number of BrewSteps 
 * @return getNumberOfSteps number of BrewSteps 
 */  
    public Integer getNumberOfSteps() {
        return brewSteps.size();
    }
/**
 * Create BrewReceipe instance from json file  
 * @param recipeName name of receipe, also file name 
 * @return getBrewRecipeFromFile Instance of BrewReceipe 
 */  

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
/**
 * get a list of all BrewSteps   <br>
 * @return getbrewSteps list of BrewSteps  
 */  
    public List<BrewStep> getbrewSteps() {
        return brewSteps;
    }
/**
 * get a BrewStep of BrewReceipe
 * @param position sequence number of BrewStep  
 * @return getBrewStepbyPosition BrewStep 
 */     
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

/**
 * remove a BrewStep from BrewReceipe <br>
 * @param position sequence number of BrewStep  
 * @return deleteBrewStep true = successful 
 */  

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

/**
 * get all Ingredient of BrewReceipe (from all BrewSteps) <br>
 * formatted as string for each ingredient and ordered by type of ingredient
 * @return AllIngredients List of Ingredient
 */      

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

/**
 * get all Ingredient of BrewReceipe (from all BrewSteps)
 * @return AllIngredients List of Ingredient
 */      

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

/**
 * get BrewStep of an Ingredient based on list position<br>
 * If a ingredient is changed it must be done a traceback to the brew step
 * @return getIngredBrewstep BrewStep of ingredient 
 * @param listPosition position of ingredient in list of method getIngredients
 */      

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

/**
 * get Ingredient based on list position
 * @return getIngredbyPosition Ingredient 
 * @param listPosition position of ingredient in list of method getIngredients
 */         

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

/**
 * add a new BrewStep to receipe 
 * @param brewStep instance of BrewStep
 * @param position position after which the step is inserted in the receipe
 */          

    public void addBrewStep (BrewStep brewStep, Integer position) {
        if (brewSteps.size() > position) {
            brewSteps.add(position, brewStep);
        } else {
            brewSteps.add(brewStep);
        }
    } 
/**
 * move an Ingred to another BrewStep
 * @param position number of source BrewStep
 * @param brewStepNo number of target BrewStep
 */        

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

/**
 * Write BrewReceipe to file in json format
 */       

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
