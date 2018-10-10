package de.dirkgasser.brauen;

import static de.dirkgasser.brauen.BrewComputerMain.recipeframe;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Combined Listener Class for ReceipeFrame, part BrewSteps <br>
 * The class implements ItemListner for text fields and <br>
 * ActionListiner for buttons <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class BrewStepKeyListner extends java.awt.event.KeyAdapter
                                implements ItemListener, ActionListener {
    private JTextField field;
    private JComboBox comboBox;
    private Integer step;
    private String stringValue;
    private Integer intValue;
    private Double doubValue;
    private Boolean manual;
    private Button button;
    private String newdelete;
    private String attribute;
    private BrewRecipe brewRecipe;
/**
 * Creator for TextFields 
 * @param field JTextField which is observed
 * @param step BrewStep of BrewRecipe to which this field belongs 
 * @param attribute name of attribute of BrewStep, will be updated if field is changed 
 * @param brewRecipe BrewRecipe to which BrewStep belongs 
 */                           
    public BrewStepKeyListner (JTextField field, Integer step, String attribute,  BrewRecipe brewRecipe) {
        this.field = field;
        this.step = step;
        this.brewRecipe = brewRecipe;
        this.attribute = attribute;
    }
/**
 * Creator for JComboBox
 * @param field JComboBox which is observed
 * @param step BrewStep of BrewRecipe to which this field belongs 
 * @param attribute name of attribute of BrewStep, will be updated if field is changed
 * @param brewRecipe BrewRecipe to which BrewStep belongs 
 */
    public BrewStepKeyListner (JComboBox field, Integer step, String attribute, BrewRecipe brewRecipe) {
        this.comboBox = field;
        this.step = step;
        this.brewRecipe = brewRecipe;
        this.attribute = attribute;
    }
/**
 * Creator for Button
 * @param button Button which is observed
 * @param step BrewStep of BrewRecipe to which this field belongs 
 * @param attribute name of attribute of BrewStep, will be updated if field is changed
 * @param brewRecipe BrewRecipe to which BrewStep belongs 
 */
    public BrewStepKeyListner (Button button, Integer step, String attribute, BrewRecipe brewRecipe) {
        this.button = button;
        this.step = step;
        this.brewRecipe = brewRecipe;
        this.attribute = attribute;
    }
    
    //keyReleased is used for all JText fields
/**
 * KeyReleased is automated called when instance of class is injected <br>
 * At this point the instance of this class knows which field is changed <br>
 * because it is given to this instance when the instance is create
 * @param evt 
 */
    public void keyReleased(java.awt.event.KeyEvent evt) {
               if (attribute.equals("Duration")) {
                   try {
                       if (field.getText().length() == 0) {
                           intValue = 0;
                           brewRecipe.getBrewStepbyPosition(step).setDuration(intValue);
                       //    field.setText(intValue.toString());
                       } else {
                           intValue = Integer.parseInt(field.getText());
                           brewRecipe.getBrewStepbyPosition(step).setDuration(intValue);
                       }
                   }
                   catch (java.lang.NumberFormatException e) {
                       field.setText(brewRecipe.getBrewStepbyPosition(step).getDuration().toString());
                    };
               }  
               if (attribute.equals("Description")) {
                       stringValue = field.getText();
                       brewRecipe.getBrewStepbyPosition(step).setDescription(stringValue);
               }      
               if (attribute.equals("Temperatur")) {
                   try {
                       if (field.getText().length() == 0) {
                           intValue = 0;
                       } else {
                           intValue = Integer.parseInt(field.getText());
                           if (intValue > 100) {
                                intValue = 100;
                                field.setText("100");
                            }
                       }
                       brewRecipe.getBrewStepbyPosition(step).setTemperatur(intValue);
                       }
                   catch (java.lang.NumberFormatException e) {
                       field.setText(brewRecipe.getBrewStepbyPosition(step).getTemperatur().toString());
                    };
               }  
            }
        public void keyPressed(java.awt.event.KeyEvent evt) {                                         
        // TODO add your handling code here:
        //    this.keyTyped(evt);
        }  
         public void keyTyped(java.awt.event.KeyEvent evt) {                                         
        // TODO add your handling code here:
        //    this.keyTyped(evt);
        }  

    //itemStateChanged is used for all Comboboxes, 
    //only selected item is processed, unselect is passed thru
    @Override
    public void itemStateChanged(ItemEvent ie) {
         if (ie.getStateChange() == ItemEvent.SELECTED) {
            if (attribute.equals("Manual")) {
                if (comboBox.getSelectedIndex() == 0) {
                   brewRecipe.getBrewStepbyPosition(step).setManual(TRUE);
                } else {
                   brewRecipe.getBrewStepbyPosition(step).setManual(FALSE);
                }  
            }
         }
    }
//actionPerformed is used for buttons
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (attribute.equals("Delete")) {
            if (!brewRecipe.deleteBrewStep(step)) {
                JOptionPane.showMessageDialog(recipeframe, "Der Brauschritt hat noch Zutaten");
            } else {
                recipeframe.readRecipe();
            }
        }
        if (attribute.equals("New")) {
            brewRecipe.addBrewStep(new BrewStep("Neu", 0, false, 0), step);
            recipeframe.readRecipe();
        }
    }

}
