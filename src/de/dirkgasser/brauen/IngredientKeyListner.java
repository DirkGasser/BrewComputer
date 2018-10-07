/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Dirk
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
    
    public IngredientKeyListner (JTextField field, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.field = field;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
    public IngredientKeyListner (JComboBox comboBox, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.comboBox = comboBox;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
    public IngredientKeyListner (JButton button, Integer listPosition, String attribute, BrewRecipe brewRecipe) {
        this.button = button;
        this.listPosition = listPosition;
        this.attribute = attribute;
        this.brewRecipe = brewRecipe;
    }
    
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
