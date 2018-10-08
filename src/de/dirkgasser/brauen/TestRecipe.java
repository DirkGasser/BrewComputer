/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import java.io.IOException;

/**
 *
 * @author Dirk
 */
public class TestRecipe {
    static Ingredient ingred;
    static BrewStep brewStep;
    static BrewRecipe brewRecipe;
    public TestRecipe() {
        
    }
    public static BrewRecipe getTestRecipe () {
       ingred = new Ingredient("Brauwatter", 17, "l", TypeofIngredient.WATER, PropertyofIndredient.NONE, 0);
       brewStep = new BrewStep("Hauptguss", ingred, 17, Boolean.TRUE, 0);
       brewRecipe = new BrewRecipe("Altbier",brewStep);
     
       ingred = new Ingredient("1 - Bömisches Malz", 200, "g", TypeofIngredient.MALT, PropertyofIndredient.EBC, 8);
       brewStep = new BrewStep("Einmaischen", ingred, 17, Boolean.FALSE, 57);
       ingred = new Ingredient("2 - Hamburger Malz", 1500, "g", TypeofIngredient.MALT, PropertyofIndredient.EBC, 20);
       brewStep.addIngredient(ingred);
       ingred = new Ingredient("3 - Nachguss", 13, "l", TypeofIngredient.WATER, PropertyofIndredient.NONE, 0);
       brewStep.addIngredient(ingred);
       brewRecipe.addBrewStep(brewStep, 2);
       
       brewStep = new BrewStep("Maltoserast",45, Boolean.TRUE, 62);
       brewRecipe.addBrewStep(brewStep, 3);
    
       ingred = new Ingredient("Hallerhauser Aromahopen", 20, "g", TypeofIngredient.HOPE, PropertyofIndredient.ALPHA, 7.5);
       brewStep = new BrewStep("Würze Kocken", ingred, 17, Boolean.TRUE, 100);
       brewRecipe.addBrewStep(brewStep, 4);

       return brewRecipe;
    }
}
