/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Dirk
 */
public class TestMain {
    static Ingredient ingred;
    static BrewStep brewStep;
    static BrewRecipe brewRecipe;
    public static void main (String[] args) throws IOException {
       ingred = new Ingredient("Brauwatter", 17, "l", TypeofIngredient.WATER, PropertyofIndredient.NONE, 0);
       brewStep = new BrewStep("Hauptguss", ingred, 17, Boolean.TRUE, Integer.MIN_VALUE);
       brewRecipe = new BrewRecipe("Test3",brewStep);
       ingred = new Ingredient("Bömisches Malz", 200, "g", TypeofIngredient.MALT, PropertyofIndredient.EBC, 8);
       brewStep = new BrewStep("Malzgabe", ingred, 17, Boolean.TRUE, Integer.MIN_VALUE);
       ingred = new Ingredient("Hamburger Malz", 1500, "g", TypeofIngredient.MALT, PropertyofIndredient.EBC, 20);
       brewStep.addIngredient(ingred);
       brewRecipe.addBrewStep(brewStep, Integer.SIZE);
       for (String ing:brewRecipe.AllIngredients()) {
           System.out.println(ing);
       }
       for (BrewStep bstep : brewRecipe.getbrewSteps()) {
           System.out.println(bstep.description);
           bstep.description = "neuer Schritt";
       }
       
       for (BrewStep bstep : brewRecipe.getbrewSteps()) {
           System.out.println(bstep.description);
       }
       //Gson gson = new Gson();
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       String json = gson.toJson(brewRecipe);
       System.out.println(json);
       
       brewRecipe.writeBrewRecipe();
       RecipeNames rcn = RecipeNames.getRecipesFromFolder();
       System.out.println(rcn.listAll());

	BrewRecipe rep2 = BrewRecipe.getBrewRecipeFromFile("Test");
        for (String ing:rep2.AllIngredients()) {
           System.out.println(ing);
        }
    }
}
