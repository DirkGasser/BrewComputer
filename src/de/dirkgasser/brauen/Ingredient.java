/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import java.text.DecimalFormat;

/**
 *
 * @author Dirk
 */
public class Ingredient {
    String descripton;
    double amount;
    String unit;
    TypeofIngredient ingredType;
    PropertyofIndredient property;
    double propertyValue;
    public Ingredient (String description, double amount, String unit, 
                        TypeofIngredient ingredType, PropertyofIndredient property,
                       double propertyValue) {
        this.descripton = description;
        this.amount = amount;
        this.ingredType = ingredType;
        this.property = property;
        this.propertyValue = propertyValue;
        this.unit = unit;        
    }

    public String getDescripton() {
        return descripton;
    }

    public double getAmount() {
        return amount;
    }
    public String getAmountF() {
        DecimalFormat df2 = new DecimalFormat("#.00"); 
        DecimalFormat df0 = new DecimalFormat("#"); 
        if ((amount % 1) == 0) {
            return df0.format(amount);
        } else {
            return df2.format(amount);
        }
    }
    

    public String getUnit() {
        return unit;
    }

    public TypeofIngredient getIngredType() {
        return ingredType;
    }
    
        
    public String getIngredTypeF() {
        switch (ingredType) {
            case WATER:
                return "Wasser";
            case HOPE:
                return "Hopfen";
            case MALT:
                return "Malz";
            case YEAST:
                return "Hefe";               
        }
        return " ";
    }

    public PropertyofIndredient getProperty() {
        return property;
    }
    public String getPropertyF() {
         switch (property) {
            case ALPHA:
                return "Alpha %";
            case EBC:
                return "EBC";            
        }
        return "n/a";
    }

    public double getPropertyValue() {
        return propertyValue;
    }
    
    public String getPropertyValueF() {
        DecimalFormat df2 = new DecimalFormat("#.00"); 
        DecimalFormat df0 = new DecimalFormat("#"); 
        if ((propertyValue % 1) == 0) {
            return df0.format(propertyValue);
        } else {
            return df2.format(propertyValue);
        }
    }
    
    public void setIngredType(TypeofIngredient ingredType) {
        this.ingredType = ingredType;
    }
    
    public void setDescription(String description) {
        this.descripton = description;
    }
     public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setProperty(PropertyofIndredient property){
        this.property = property;
    }
    public void setPropertyValue(double propertyValue){
        this.propertyValue = propertyValue;
    }
    public void setUnit(String unit){
        this.unit = unit;
    }
}
