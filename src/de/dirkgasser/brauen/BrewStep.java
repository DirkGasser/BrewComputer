package de.dirkgasser.brauen;
import java.util.ArrayList;
import java.util.List;

/**
 * A BrewStep is an action in a BrewRecipe <br>
 * It consists of temperature and duration and a list of all ingredients <br>
 * which are put in in this step <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class BrewStep {
    public String description;
    List<Ingredient> ingredients = new ArrayList<>();
    public Integer duration;
    public Boolean manual;
    public Integer temperatur;
/**
 * @param description Description/Name of this BrewStep
 * @param ingredients List of Ingredients which are put in in this step
 * @param duration Duration of BrewStep in minutes
 * @param manual true = start of step requires manual confirmation
 * @param temperatur temperatur of this step 
 */
    public BrewStep (String description, List<Ingredient> ingredients, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.ingredients = ingredients;
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 
/**
 * create BrewStep with only one ingredient
 * @param ingredients Ingredients which are put in in this step
 * @param description Description/Name of this BrewStep
 * @param duration Duration of BrewStep in minutes
 * @param manual true = start of step requires manual confirmation
 * @param temperatur temperatur of this step 
 */   
    public BrewStep (String description, Ingredient ingredients, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.ingredients.add(ingredients);
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 
/**
 * create BrewStep without ingredient
 * @param description Description/Name of this BrewStep
 * @param duration Duration of BrewStep in minutes
 * @param manual true = start of step requires manual confirmation
 * @param temperatur temperatur of this step 
 */   
   public BrewStep (String description, Integer duration, Boolean manual,
                     Integer temperatur) {
        this.description = description;
        this.duration = duration;
        this.manual = manual;
        this.temperatur = temperatur;
    } 
/**
 * create BrewStep without ingredient and without temperatur
 * @param description Description/Name of this BrewStep
 * @param duration Duration of BrewStep in minutes
 * @param manual true = start of step requires manual confirmation
 */   
     public BrewStep (String description, Integer duration, Boolean manual) {
        this.description = description;
        this.duration = duration;
        this.manual = manual;
    } 
    
/**
 * get description of BrewStep
 * @return description/name of Brewstep
 */
    public String getDescription() {
        return description;
    }
/**
 * get duration of brewstep
 * @return duration of step in minutes
 */
    public Integer getDuration() {
        return duration;
    }
/**
 * get manual start flag
 * @return true = start of step requires manual confirmation
 */
    public Boolean getManual() {
        return manual;
    }
/**
 * get temperatur which must have this step
 * @return temperatur °C
 */
    public Integer getTemperatur() {
        return temperatur;
    }
/**
 * get list of ingredients
 * @return List of all ingredients
 */
    public List<Ingredient> getIngredients () {
        return ingredients;
    }
/**
 * get list of ingredients of a specific ingredient
 * @param typ TypofIngredient which should be returned
 * @return List of Ingredient 
 */
    public List<Ingredient> getIngredients (TypeofIngredient typ) {
        List<Ingredient> ingred = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (typ.equals(ingredient.ingredType)) {
                ingred.add(ingredient);
            }
        }
        return ingred;
    }
/**
 * get a single string with all ingredients separated by CRLF
 * @return string with all ingredients 
 */
    public String listIngredients() {
        StringBuilder sbList = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
               sbList.append(ingredient.getIngredTypeF());
               sbList.append(": ");
               sbList.append(ingredient.getDescripton());
               sbList.append("\n");
               sbList.append(ingredient.getAmountF());
               sbList.append(" ");
               sbList.append(ingredient.getUnit());  
               sbList.append("\n");
               sbList.append("\n");
            }
        return sbList.toString();
    }
/**
 * check if BrewStep has ingredients
 * @return true = BrewStep has ingredient
 */  
    public Boolean hasIngredients() {
        return (!ingredients.isEmpty());
    }
/**
 * add an ingredient to BrewStep
 * @param ingredient Ingredient which should be added
 */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
/**
 * remove an ingredient from BrewStep
 * @param ingredient Ingredient which should be removed
 */
    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
/**
 * set description of BrewStep
 * @param description description/name to be set
 */
    public void setDescription(String description) {
        this.description = description;
    }
/**
 * set duration of BrewStep
 * @param duration Duration of BrewStep in minutes
 */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
/**
 * set flag for manual action 
 * @param manual flag for manual action to start step
 */
    public void setManual(Boolean manual) {
        this.manual = manual;
    }
/**
 * set temperatur of BrewStep
 * @param temperatur temperatur of Brewstep
 */
    public void setTemperatur(Integer temperatur) {
        this.temperatur = temperatur;
    }
}
