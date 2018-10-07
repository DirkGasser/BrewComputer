/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import static de.dirkgasser.brauen.BrewComputerMain.brewRecipe;
import static de.dirkgasser.brauen.BrewComputerMain.recipeframe;

/**
 *
 * @author Dirk
 */
public class RecipeNameButtonListner implements ActionListener {
    JButton button;
    public RecipeNameButtonListner (JButton button) {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        brewRecipe = BrewRecipe.getBrewRecipeFromFile(button.getName());
        recipeframe.readRecipe();
    }
}
