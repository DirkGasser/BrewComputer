/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dirk
 */
public class RecipeNames {
    private List<String> recipeNames;
    
    public RecipeNames (List<String> recipeNames) {
        this.recipeNames = recipeNames;
    }
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
    public void addRecipeName (String name) {
        recipeNames.add(name);
    }
    public List<String> getAllNames() {
        return recipeNames;
    }
    public Integer getNumberOfRecipes(){
        return recipeNames.size();
    }
    public String listAll(){
        StringBuilder sb = new StringBuilder();
        for (String name: recipeNames) {
             sb.append(name);
             sb.append("\n");
        }
        return sb.toString();
    }
    public boolean isinList(String name) {
        return Arrays.asList(recipeNames).contains(name);
    }
}
