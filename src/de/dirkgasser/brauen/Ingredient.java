package de.dirkgasser.brauen;

import java.text.DecimalFormat;

/**
 * Ingredient of brew recipe
 * Ingredients are part of a brew step 
 * @author Dirk Gasser
 * @version 1.0
 */
public class Ingredient {
    String descripton;
    double amount;
    String unit;
    TypeofIngredient ingredType;
    PropertyofIndredient property;
    double propertyValue;
/**
 * 
 * @param description description of ingredient
 * @param amount amount of ingredient in 'unit'
 * @param unit unit in which ingredient in measured
 * @param ingredType TypeofIngredient (water, hope, malt)
 * @param property PropertyofIngredient (IBU for malt or alpha for hope)
 * @param propertyValue  Value of property 
 */
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

    /**
     * get description of ingredient
     * @return - description of ingredient
     */
    public String getDescripton() {
        return descripton;
    }

    /**
     * get amount of ingredient
     * @return - amount of ingredient 
     */
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
    
/**
 * getUnit of ingredient
 * @return unit of Ingredient
 */
    public String getUnit() {
        return unit;
    }

    /**
     * get type of ingredient (malt, water, hope)
     * @return ingredType
     */
    public TypeofIngredient getIngredType() {
        return ingredType;
    }
    
/**
 * get type of ingredient as text
 * @return type of ingredient as text
 * @return type of ingredient as text      
 */      
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

    /**
     * get Property of ingredient in Unit
     * @return Property of ingredient
     */
    public PropertyofIndredient getProperty() {
        return property;
    }
    
    /**
     * get property as string
     * @return property as string
     */
    public String getPropertyF() {
         switch (property) {
            case ALPHA:
                return "Alpha %";
            case EBC:
                return "EBC";            
        }
        return "n/a";
    }

    /**
     * get value in 'property'
     * @return value in 'property'
     */
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
    
/**
 * set type of ingredient
 * @param ingredType TypeofIngretient (Water, Hope, Malt)
 */
    public void setIngredType(TypeofIngredient ingredType) {
        this.ingredType = ingredType;
    }

/**
 * set description of ingredient
 * @param description description of ingredient
 */    
    public void setDescription(String description) {
        this.descripton = description;
    }
/**
 * set amount of ingredient 
 * @param amount amount of ingredient
 */
     public void setAmount(double amount) {
        this.amount = amount;
    }
/**
 * set PropertyofIngredient 
 * @param property PropertyofIngredient 
 */
    public void setProperty(PropertyofIndredient property){
        this.property = property;
    }
/**
 * set property of ingredient
 * @param propertyValue property of ingredient
 */
    public void setPropertyValue(double propertyValue){
        this.propertyValue = propertyValue;
    }
/**
 * set unit of ingredient
 * @param unit unit of ingredient
 */
    public void setUnit(String unit){
        this.unit = unit;
    }
}
