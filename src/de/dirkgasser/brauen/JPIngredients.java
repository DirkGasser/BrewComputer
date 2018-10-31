package de.dirkgasser.brauen;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/**
 * Panel to shown all ingredients in the RecipeFrame <br>
 * IngredientKeyListner is used to store any change in the BrewRecipe <br>
 * jpIngredients panel of RecipeFrame is replaced by this Panel 
 * @author Dirk Gasser
 * @version 1.0
 */
public class JPIngredients extends javax.swing.JPanel {

/**
 * Create a JPanel with all ingredients for edit
 * @param brewRecipe BrewRecipe which includes all ingredients to be shown
 */    
    public JPIngredients (BrewRecipe brewRecipe) {
        Integer step = 1;
        Integer high;
        JComboBox[] jcIngredType = new JComboBox[50];
        JComboBox[] jcProperty = new JComboBox[50];
        JComboBox[] jcStepconnect = new JComboBox[50];
        JTextField[] jtAmount = new JTextField[50];
        JTextField[] jtIngredDescription = new JTextField[50];
        JTextField[] jtPropertyValue = new JTextField[50];
        JTextField[] jtUnit = new JTextField[50];
        JButton[] jbDeleteIngred = new JButton[50];
        String[] jcStepItem = new String[brewRecipe.getbrewSteps().size()];
        JButton jbNeu = new JButton();
        jbNeu.setText("Neu");
        jbNeu.addActionListener(new IngredientKeyListner(jbNeu, brewRecipe.getbrewSteps().size(), "New", brewRecipe));
    
        high = 45 + 35 * brewRecipe.getIngredients().size();
        this.setMinimumSize(new java.awt.Dimension(100, 190));
        this.setPreferredSize(new java.awt.Dimension(600, high));
       
        Integer i = 0;
        for (BrewStep brewStep : brewRecipe.getbrewSteps()) {
             jcStepItem[i] = Integer.toString(i + 1) + " " + brewStep.getDescription();
             i = i + 1;
        }
        
        GroupLayout jpIngredLayout = new GroupLayout(this);
        this.setLayout(jpIngredLayout);
        GroupLayout.ParallelGroup parallelGroup = jpIngredLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        
               
        for (Ingredient ingredient : brewRecipe.getIngredients()) {
            jcIngredType[step] = new JComboBox<>();
            jcIngredType[step].setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wasser", "Malz", "Hopfen", "Hefe" }));
            jcIngredType[step].setSelectedItem(ingredient.getIngredTypeF());
            jcIngredType[step].addItemListener(new IngredientKeyListner(jcIngredType[step], step, "TypeofIngredient", brewRecipe));
            jcProperty[step]  = new JComboBox<>();
            jcProperty[step].setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "n/a", "EBC", "Alpha %" }));
            jcProperty[step].setSelectedItem(ingredient.getPropertyF());
            jcProperty[step].addItemListener(new IngredientKeyListner(jcProperty[step], step, "Property", brewRecipe));
            jcStepconnect[step]  = new JComboBox<>();
            jcStepconnect[step].setModel(new javax.swing.DefaultComboBoxModel<>(jcStepItem));
            jcStepconnect[step].setSelectedIndex(brewRecipe.getIngredBrewstep(step));
            jcStepconnect[step].addItemListener(new IngredientKeyListner(jcStepconnect[step], step, "Stepconnect", brewRecipe));
            jtAmount[step] = new JTextField();
            jtAmount[step].setText(ingredient.getAmountF());
            jtAmount[step].addKeyListener(new IngredientKeyListner(jtAmount[step], step, "Amount", brewRecipe));
            jtIngredDescription[step] = new JTextField();
            jtIngredDescription[step].setText(ingredient.getDescripton());
            jtIngredDescription[step].addKeyListener(new IngredientKeyListner(jtIngredDescription[step], step, "Description", brewRecipe));
            jtPropertyValue[step] = new JTextField();
            jtPropertyValue[step].setText(ingredient.getPropertyValueF());
            jtPropertyValue[step].addKeyListener(new IngredientKeyListner(jtPropertyValue[step], step, "PropertyValue", brewRecipe));
            
            jtUnit[step]  = new JTextField();
            jtUnit[step].setText(ingredient.getUnit());   
            jtUnit[step].addKeyListener(new IngredientKeyListner(jtUnit[step], step, "Unit", brewRecipe));
            jbDeleteIngred[step] = new JButton();
            jbDeleteIngred[step].setText("löschen");
            jbDeleteIngred[step].addActionListener(new IngredientKeyListner(jbDeleteIngred[step], step, "Delete", brewRecipe));
            
  
                  
            parallelGroup.addGroup(jpIngredLayout.createSequentialGroup()
                .addComponent(jcIngredType[step], GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
  //              .addComponent(jtIngredDescription[step], GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
                .addComponent(jtIngredDescription[step], GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jtAmount[step], GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4,4)
                .addComponent(jtUnit[step], GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                 .addGap(4, 4,4)
                .addComponent(jcProperty[step], GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addGap(4, 4,4)
                .addComponent(jtPropertyValue[step], GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jcStepconnect[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                 .addGap(4, 4,4)
                .addComponent(jbDeleteIngred[step]));
            step = step + 1;
        }
        
        jpIngredLayout.setHorizontalGroup(
            jpIngredLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpIngredLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelGroup
                .addComponent(jbNeu))
                .addContainerGap(28, Short.MAX_VALUE)));
        
        GroupLayout.SequentialGroup sequentialGroupVert = jpIngredLayout.createSequentialGroup();
        sequentialGroupVert.addContainerGap();
        
        step = 1;
        for (Ingredient ingredient : brewRecipe.getIngredients()) {
            sequentialGroupVert.addGroup(jpIngredLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(jcIngredType[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jtIngredDescription[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jtAmount[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jtUnit[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jcProperty[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jcStepconnect[step],GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
            .addComponent(jtPropertyValue[step],GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
            .addComponent(jbDeleteIngred[step]))
            .addGap(4,4,4);        
 //           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            step = step + 1;
        }
        
        sequentialGroupVert.addComponent(jbNeu);
            
        jpIngredLayout.setVerticalGroup(
            jpIngredLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroupVert.addContainerGap(125, Short.MAX_VALUE)));
        
    }
    
}
