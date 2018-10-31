package de.dirkgasser.brauen;

import static de.dirkgasser.brauen.BrewComputerMain.recipeframe;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Listener Class which update immediately the ingredient of the brew recipe <br>
 * when field in screen is changed
 * @author Dirk Gasser
 * @version 1.0
 */
public class IngredientKeyListner extends java.awt.event.KeyAdapter 
                                  implements ItemListener, ActionListener {
    private JTextField field;
    private JComboBox comboBox;
    private Integer listPosition;
    private String attribute;
    private Integer intValue;
    private Double doubValue;
    private BrewRecipe brewRecipe;
    private JButton button;
/**
 * Create Listner for JTextField
 * @param field JTextField which is observed 
 * @param listPosition Position of the observed ingredient in the brew recipe
 * @param attribute Name of the Ingredients attribute to be updated 
 * @param brewRecipe BrewRecipe which includes the observed ingredient 
 */    
    public IngredientKeyListner (JTextField field, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.field = field;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
/**
 * Create Listner of JComboBox
 * @param comboBox JComboBox which is observed 
 * @param listPosition Position of the observed ingredient in the brew recipe
 * @param attribute Name of the Ingredients attribute to be updated
 * @param brewRecipe BrewRecipe which includes the observed ingredient
 */
    public IngredientKeyListner (JComboBox comboBox, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.comboBox = comboBox;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
/**
 * Create Listner of JButton
 * @param button JButton which is observed
 * @param listPosition Position of the observed ingredient in the brew recipe
 * @param attribute Name of the Ingredients attribute to be updated
 * @param brewRecipe BrewRecipe which includes the observed ingredient
 */
    public IngredientKeyListner (JButton button, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.button = button;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
/**
 * Take new value from screen field and store in into ingredient of brew recipe
 * @param evt not used all information is taken from class instance given from constructor
 */   
    public void keyReleased(java.awt.event.KeyEvent evt) {      
        if (attribute.equals("Description")) {
            brewRecipe.getIngredbyPosition(listPosition).setDescription(field.getText());
        }
        if (attribute.equals("Unit")) {
            brewRecipe.getIngredbyPosition(listPosition).setUnit(field.getText());
        }
        if (attribute.equals("Amount")) {
            try {
                    if (field.getText().length() == 0) {
                       doubValue = 0d;
                       brewRecipe.getIngredbyPosition(listPosition).setAmount(doubValue);
                    } else {
                        doubValue = Double.parseDouble(field.getText().replaceAll(",","."));
                        brewRecipe.getIngredbyPosition(listPosition).setAmount(doubValue);
                    }
                }
            catch (java.lang.NumberFormatException e) {
                field.setText(brewRecipe.getIngredbyPosition(listPosition).getAmountF());
            };        
        }
        if (attribute.equals("PropertyValue")) {
            try {
                    if (field.getText().length() == 0) {
                       doubValue = 0d;
                       brewRecipe.getIngredbyPosition(listPosition).setPropertyValue(doubValue);
                    } else {
                        doubValue = Double.parseDouble(field.getText().replaceAll(",","."));
                        brewRecipe.getIngredbyPosition(listPosition).setPropertyValue(doubValue);
                    }
                }
            catch (java.lang.NumberFormatException e) {
                field.setText(brewRecipe.getIngredbyPosition(listPosition).getPropertyValueF());
            };        
        }
        if (attribute.equals("Stepconnect")) {
            brewRecipe.transferIngred(listPosition, comboBox.getSelectedIndex());
        }
    }
/**
 * Take new value from screen drop list and store in into ingredient of brew recipe
 * @param ie not used all information is taken from class instance given from constructor
 */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getStateChange() == ItemEvent.SELECTED) {
            if (attribute.equals("TypeofIngredient")) {
                if (comboBox.getSelectedItem().equals("Wasser")) {
                    brewRecipe.getIngredbyPosition(listPosition).setIngredType(TypeofIngredient.WATER);
                }
                else if (comboBox.getSelectedItem().equals("Malz")) {
                    brewRecipe.getIngredbyPosition(listPosition).setIngredType(TypeofIngredient.MALT);
                }
                else if (comboBox.getSelectedItem().equals("Hopfen")) {
                    brewRecipe.getIngredbyPosition(listPosition).setIngredType(TypeofIngredient.HOPE);
                }
                else if (comboBox.getSelectedItem().equals("Hefe")) {
                    brewRecipe.getIngredbyPosition(listPosition).setIngredType(TypeofIngredient.YEAST);
                }
                recipeframe.readRecipe();
            }
            if (attribute.equals("Property")) {
                if (comboBox.getSelectedItem().equals("n/a")) {
                    brewRecipe.getIngredbyPosition(listPosition).setProperty(PropertyofIndredient.NONE);
                }
                else if (comboBox.getSelectedItem().equals("EBC")) {
                     brewRecipe.getIngredbyPosition(listPosition).setProperty(PropertyofIndredient.EBC);
                }
                else if (comboBox.getSelectedItem().equals("Alpha %")) {
                     brewRecipe.getIngredbyPosition(listPosition).setProperty(PropertyofIndredient.ALPHA);
                };
            }
            if (attribute.equals("Stepconnect")) {
                brewRecipe.transferIngred(listPosition,comboBox.getSelectedIndex());
                recipeframe.readRecipe();
            }
            
        }
    }
/**
 * Take new value from screen button and store in into ingredient of brew recipe
 * @param ae not used all information is taken from class instance given from constructor
 */
    @Override
    public void actionPerformed(ActionEvent ae) {
       if (attribute.equals("New")) {
            brewRecipe.getBrewStepbyPosition(brewRecipe.getIngredBrewstep(listPosition) + 1).addIngredient(new Ingredient("neu",0d," ",TypeofIngredient.MALT,PropertyofIndredient.NONE,0d));
            recipeframe.readRecipe();
        }
       if (attribute.equals("Delete")) {
            brewRecipe.getBrewStepbyPosition(brewRecipe.getIngredBrewstep(listPosition) + 1).deleteIngredient(brewRecipe.getIngredbyPosition(listPosition));
            recipeframe.readRecipe();
        }
    }
}
