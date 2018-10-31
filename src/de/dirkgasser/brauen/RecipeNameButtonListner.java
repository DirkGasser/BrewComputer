package de.dirkgasser.brauen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import static de.dirkgasser.brauen.BrewComputerMain.brewRecipe;
import static de.dirkgasser.brauen.BrewComputerMain.recipeframe;

/**
 * Listner for a JButton for a BrewRecipe in JPRecipeNames <br>
 * If Button is select the BrewRecipe is move to RecipeFrame to be edited 
 * @author Dirk Gasser
 * @version 1.0
 */
public class RecipeNameButtonListner implements ActionListener {
    JButton button;
/**
 * Create Listner for a BrewRecipe to be selected
 * @param button JButton which represent the brew Recipe
 */
    public RecipeNameButtonListner (JButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        brewRecipe = BrewRecipe.getBrewRecipeFromFile(button.getName());
        recipeframe.readRecipe();
    }
}
