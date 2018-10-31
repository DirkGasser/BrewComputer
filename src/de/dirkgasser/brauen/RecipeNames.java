package de.dirkgasser.brauen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * List of all BrewRecipes Names as String
 * @author Dirk Gasser
 * @version 1.0
 */
public class RecipeNames {
    private List<String> recipeNames;

/**
 * standard creator, usually method getRecipesFromFolder is used as creator
 * @param recipeNames List of all recipe names
 */    
    public RecipeNames (List<String> recipeNames) {
        this.recipeNames = recipeNames;
    }
/**
 * Static method as creator for RecipeNames <br>
 * Read all name from file system 
 * @return instance of RecipeNames
 */
    public static RecipeNames getRecipesFromFolder() {
        List<String> names = new ArrayList<>();
        String fileName;
        String nameNoEnding;
        File folder = new File(System.getProperty("user.home"));
        File[] allNames = folder.listFiles((parent,name) -> name.endsWith(".brc")); 
        for (Integer i=0; allNames != null && i < allNames.length; i++) {
           fileName = allNames[i].getName(); 
           nameNoEnding = (fileName.lastIndexOf(".") > -1 )?fileName.substring(0, fileName.lastIndexOf(".")): fileName;
           names.add(nameNoEnding);
        }
        return new RecipeNames(names);
    }
/**
 * add a new recipe name
 * @param name name of a recipe
 */
    public void addRecipeName (String name) {
        recipeNames.add(name);
    }
/**
 * get a list of all recipe names
 * @return list of all recipe names
 */
    public List<String> getAllNames() {
        return recipeNames;
    }
/**
 * get number of all recipes
 * @return number of recipes
 */
    public Integer getNumberOfRecipes(){
        return recipeNames.size();
    }
/**
 * get a list of all recipe names as String separated by CRLF
 * @return recipe names separated by CRLF
 */
    public String listAll(){
        StringBuilder sb = new StringBuilder();
        for (String name: recipeNames) {
             sb.append(name);
             sb.append("\n");
        }
        return sb.toString();
    }
/**
 * check if a recipe already exsists
 * @param name name of a recipe
 * @return true = recipe already exists
 */
    public boolean isinList(String name) {
        return Arrays.asList(recipeNames).contains(name);
    }
}
