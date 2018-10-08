/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import static de.dirkgasser.brauen.BrewComputerMain.brewRecipe;
import static java.lang.Integer.min;
import javax.swing.GroupLayout;
import javax.swing.JButton;

/**
 *
 * @author Dirk
 */
public class JPRecipeNames extends javax.swing.JPanel {
    private RecipeNames recipeNames;
    public JPRecipeNames (Boolean firstRecipe) {
        Integer step = 1;
        Integer high;
        
        recipeNames = RecipeNames.getRecipesFromFolder();
        JButton[] jbRecipeName = new JButton[recipeNames.getNumberOfRecipes() + 1];
        
        high = 45 + 35 * recipeNames.getAllNames().size();
        this.setMinimumSize(new java.awt.Dimension(10, 200));
        this.setPreferredSize(new java.awt.Dimension(50, high));
        
        GroupLayout jpNamesLayout = new GroupLayout(this);
        this.setLayout(jpNamesLayout);
        GroupLayout.ParallelGroup parallelGroup = jpNamesLayout.createParallelGroup(GroupLayout.Alignment.LEADING); 
        
        for (String name : recipeNames.getAllNames()) {
            jbRecipeName[step] = new JButton();
            jbRecipeName[step].setText(name.substring(0, min(15,name.length())));
            jbRecipeName[step].setName(name);
            jbRecipeName[step].addActionListener(new RecipeNameButtonListner(jbRecipeName[step]));
            parallelGroup.addComponent(jbRecipeName[step]);
            step++;
        }
        if (step > 1 && firstRecipe) {
            brewRecipe = BrewRecipe.getBrewRecipeFromFile(jbRecipeName[1].getName()); 
        }
      
        jpNamesLayout.setHorizontalGroup(
            jpNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpNamesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelGroup)
                .addContainerGap(61, Short.MAX_VALUE)));
       
        GroupLayout.SequentialGroup sequentialGroupVert = jpNamesLayout.createSequentialGroup();
        sequentialGroupVert.addContainerGap();
        
        step = 1;
        for (String name : recipeNames.getAllNames()) {
        //    jbRecipeName[step].setText(name);
            jbRecipeName[step].setText(name.substring(0, min(14,name.length())));
            jbRecipeName[step].setName(name);
            sequentialGroupVert.addContainerGap()
                    .addComponent(jbRecipeName[step]);
            step++;
        }
        jpNamesLayout.setVerticalGroup(
            jpNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroupVert
                .addContainerGap(388, Short.MAX_VALUE)));
    }
    
}
