package de.dirkgasser.brauen;

import java.awt.Button;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/**
 * JPanel which shows all BrewSteps of a BrewRecipe in the RecipeFrame <br>
 * BrewStepKeyListner is used to store any change in the BrewRecipe <br>
 * jpBrewProcess panel of RecipeFrame is replaced by this Panel 
 * @author Dirk Gasser
 * @version 1.0
 */
public class JPRecipeProcess extends javax.swing.JPanel {
   
/**
 * Create a JPanel with all BrewSteps for edit
 * @param brewRecipe BrewRecipe which includes BrewSteps to be shown here 
 */   
    public JPRecipeProcess (BrewRecipe brewRecipe) {
        Integer step = 1;
        Integer high;
        JTextField[] jtBezeichnung = new JTextField[50];
        JTextField[] jtDauer = new JTextField[50];
        JTextField[] jtSchritt = new JTextField[50];
        JTextField[] jtTemperatur = new JTextField[50];
        JComboBox[] jcbStart = new JComboBox[50];
        Button[] bDeletebs = new Button[50];
        Button[] bNewStep = new Button[50];
        JLabel[] lbCelsius = new JLabel[50];
        JLabel[] lbMin = new JLabel[50];
        
        high = 15 + 40 * brewRecipe.getbrewSteps().size();
        this.setMinimumSize(new java.awt.Dimension(100, 190));
        this.setPreferredSize(new java.awt.Dimension(600, high));
        this.setFont(new java.awt.Font("Dialog", 1, 12));
        
        GroupLayout jpBrewProcessLayout = new GroupLayout(this);
        this.setLayout(jpBrewProcessLayout);
                
        ParallelGroup parallelGroup = jpBrewProcessLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        
        for (BrewStep brewStep : brewRecipe.getbrewSteps()) {
            jtBezeichnung[step] = new JTextField();  
            jtBezeichnung[step].addKeyListener(new BrewStepKeyListner(jtBezeichnung[step], step, "Description", brewRecipe));
            jtBezeichnung[step].setPreferredSize(new java.awt.Dimension(80, 30));
            jtDauer[step] = new JTextField();
            jtDauer[step].addKeyListener(new BrewStepKeyListner(jtDauer[step], step, "Duration", brewRecipe));
            jtSchritt[step] = new JTextField();
            jtSchritt[step].setEditable(false);
            jtTemperatur[step] = new javax.swing.JTextField();
            jtTemperatur[step].addKeyListener(new BrewStepKeyListner(jtTemperatur[step], step, "Temperatur", brewRecipe));
            jcbStart[step] = new JComboBox<>();
            jcbStart[step].setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "manuell", "automatisch" }));
            jcbStart[step].addItemListener(new BrewStepKeyListner(jcbStart[step], step, "Manual", brewRecipe));
            lbMin[step] = new javax.swing.JLabel();
            lbCelsius[step] = new javax.swing.JLabel();
            bDeletebs[step] = new java.awt.Button();
            bDeletebs[step].setLabel("löschen");
            bDeletebs[step].addActionListener(new BrewStepKeyListner(bDeletebs[step], step, "Delete", brewRecipe));
            bNewStep[step] = new java.awt.Button();
            bNewStep[step].addActionListener(new BrewStepKeyListner(bNewStep[step], step, "New", brewRecipe));
            
            lbMin[step].setText("min.");
            lbCelsius[step].setText("°C");

            bNewStep[step].setLabel("neu");
                        
            jtBezeichnung[step].setText(brewStep.getDescription());
            jtDauer[step].setText(brewStep.getDuration().toString());
            jtTemperatur[step].setText(brewStep.getTemperatur().toString());
            jtSchritt[step].setText(step.toString());
            if (brewStep.getManual()) {
                jcbStart[step].setSelectedItem("manuell");
            } else {
                jcbStart[step].setSelectedItem("automatisch");
            }
                    
            parallelGroup.addGroup(jpBrewProcessLayout.createSequentialGroup()
                        .addComponent(jtSchritt[step], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 10)
                        .addComponent(jtBezeichnung[step], GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 10)
                        .addComponent(jcbStart[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                         .addGap(7, 7, 10)
                        .addComponent(jtDauer[step], GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbMin[step])
                         .addGap(7, 7, 10)
                        .addComponent(jtTemperatur[step], GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCelsius[step])
                         .addGap(7, 7, 10)
                        .addComponent(bDeletebs[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bNewStep[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
            step = step + 1;
        }
        
         
        SequentialGroup sequentialGroup = jpBrewProcessLayout.createSequentialGroup();
        sequentialGroup.addContainerGap();
        sequentialGroup.addGroup(parallelGroup);
        sequentialGroup.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        sequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50  , 200);
        
        jpBrewProcessLayout.setHorizontalGroup(
            jpBrewProcessLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroup));
        
        SequentialGroup sequentialGroupVert = jpBrewProcessLayout.createSequentialGroup();
        sequentialGroupVert.addContainerGap();
        
        step = 1;
        for (BrewStep brewStep : brewRecipe.getbrewSteps()) {
             sequentialGroupVert         
                    .addGroup(jpBrewProcessLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jtSchritt[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtBezeichnung[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbStart[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtDauer[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbMin[step])
                        .addComponent(jtTemperatur[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbCelsius[step])
                        .addComponent(bNewStep[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(bDeletebs[step], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(4,4,4);   
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED); 
             step = step + 1;
        }
         
        jpBrewProcessLayout.setVerticalGroup(
            jpBrewProcessLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sequentialGroupVert.addContainerGap(98, Short.MAX_VALUE)));
    }   
}
