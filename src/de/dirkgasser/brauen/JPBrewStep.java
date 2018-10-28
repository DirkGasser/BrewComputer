package de.dirkgasser.brauen;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;

/**
 * JPanel for one BrewStep in BrewComputerFrame <br>
 * Shows all parameters and ingredient of a BrewStep <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class JPBrewStep extends JPanel {
    JTextField jtStepName;
    JTextField jtDuration;
    JTextPane jtpIngredients;
    JLabel jlDuration;
    JScrollPane jScrollPane1;
/**
 * Create a Panel for a BrewStep
 * @param brewStep BrewStep which is shown in the Jpanel
 */      
    public JPBrewStep (BrewStep brewStep) {
        jtStepName = new javax.swing.JTextField();
        jtDuration = new javax.swing.JTextField();
        jtpIngredients = new javax.swing.JTextPane();   
        jlDuration = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
      
      
        jtStepName.setFocusable(false);
        jtStepName.setRequestFocusEnabled(false);
        jtStepName.setFont(new java.awt.Font("Dialog", 1, 14)); 

        jlDuration.setText("Dauer");

        jtDuration.setFocusable(false);
        jtDuration.setRequestFocusEnabled(false);

        jtpIngredients.setFocusable(false);
        jtpIngredients.setRequestFocusEnabled(false);
        
        jtStepName.setHorizontalAlignment(JTextField.CENTER);
        jtStepName.setText(brewStep.getDescription());
        
        jtDuration.setText(brewStep.getDuration() + " Min. / " + brewStep.getTemperatur() + " °C" );
        jtDuration.setFont(new java.awt.Font("Dialog", 1, 14)); 
        jtpIngredients.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); 
        jtpIngredients.setText(brewStep.listIngredients());
        
        this.setMinimumSize(new java.awt.Dimension(200, 200));
        this.setPreferredSize(new java.awt.Dimension(200, 290));
        
        jScrollPane1.setViewportView(jtpIngredients);
        GroupLayout jpPrevStepLayout = new GroupLayout(this);
        this.setLayout(jpPrevStepLayout);
        jpPrevStepLayout.setHorizontalGroup(
            jpPrevStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrevStepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpPrevStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jtStepName)
                    .addGroup(jpPrevStepLayout.createSequentialGroup()
                        .addComponent(jlDuration)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtDuration, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpPrevStepLayout.setVerticalGroup(
            jpPrevStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrevStepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtStepName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpPrevStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlDuration)
                    .addComponent(jtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
        );
    }
}
