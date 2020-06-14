
package de.dirkgasser.brauen;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import java.io.IOException;
import de.dirkgasser.brauen.BrewComputerFrame;
import de.dirkgasser.brauen.TestFrame;
import de.dirkgasser.brauen.RecipeFrame;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Main Class to start brew computer application <br>
 * Developed for raspberry pi <br>
 * needs connection from raspberry to brew board <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class BrewComputerMain {
    public static RecipeFrame recipeframe;
    public static BrewComputerFrame brewframe;
    public static TestFrame testFrame;
    public static BrewRecipe brewRecipe;
    public static GpioController gpio;
    public static Pump pump; 
    public static TemperaturSensor temperaturSensor;
    public static Heater heater;
    public static Buzzer buzzer;
    public static AtomicInteger currentTemp = new AtomicInteger(0);
    public static AtomicInteger deltaTemp= new AtomicInteger(0);
    public static AtomicBoolean isAlive= new AtomicBoolean(true);
    private static double width;
    private static double height;
    
    
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BrewComputerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BrewComputerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BrewComputerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BrewComputerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        isAlive.set(true); 
        gpio = GpioFactory.getInstance();  
        pump = new Pump(31, 15, 16, gpio);
        temperaturSensor = new TemperaturSensor();
        heater = new Heater(29);
        buzzer = new Buzzer(23, gpio);
       
       
        brewRecipe = TestRecipe.getTestRecipe();
        testFrame = new TestFrame();
        recipeframe = new RecipeFrame();
        brewframe = new BrewComputerFrame();
        
        FullScreen.fullScreen(recipeframe, false);
       
       Thread thTemp = new Thread(new TemperaturWatcher(temperaturSensor));
       thTemp.start();
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                recipeframe.setVisible(true);
            }
        });
    }
}
