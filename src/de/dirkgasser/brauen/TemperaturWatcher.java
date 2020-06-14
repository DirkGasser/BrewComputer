package de.dirkgasser.brauen;

import static de.dirkgasser.brauen.BrewComputerMain.brewframe;
import static de.dirkgasser.brauen.BrewComputerMain.currentTemp;
import static de.dirkgasser.brauen.BrewComputerMain.deltaTemp;
import static de.dirkgasser.brauen.BrewComputerMain.isAlive;
import static de.dirkgasser.brauen.BrewComputerMain.buzzer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static de.dirkgasser.brauen.BrewComputerMain.testFrame;

/**
 * TemperaturWatcher calls every 4 seconds the TemperaturSensor <br>
 * and write the temperature in the global variable currentTemp <br>
 * If the test screen is active, the temperatur is shown there <br>
 * Also the delta temperatur is calculated to stop heater in time <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class TemperaturWatcher implements Runnable {
    private TemperaturSensor tempSensor;
    private JTextField display;
    private NumberFormat numberFormat;
    double newTemp;
    double[] tempList = new double[30];
    Integer tempListPos;
    
/**
 * Instance is started as runnable
 * @param tempSensor instance of class TemperaturSensor 
*/   
    public TemperaturWatcher (TemperaturSensor tempSensor) {
        this.tempSensor = tempSensor;
        numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
        ((DecimalFormat) numberFormat).applyPattern("###.#"); 
        tempListPos = 0;
        for (int i=0;i<30;i++) {
            tempList[i] = 0;
        }
        deltaTemp.set(0); 
        currentTemp.set(0);
    }
    
    @Override
    public void run() {
        while (isAlive.get()) { 
            newTemp = tempSensor.getTemp();
            if (newTemp < 120 && newTemp > -20 && newTemp != 0) {
                currentTemp.getAndSet((int)(100d * newTemp));
                if (tempList[tempListPos] != 0) {
                    if (newTemp < getAvgTemp()) {
                        deltaTemp.getAndSet((int)((newTemp - getAvgTemp()) * 10));
                    } else if (newTemp > 85) {
                        deltaTemp.getAndSet((int)((newTemp - getAvgTemp()) * 50));
                    } else {
                        deltaTemp.getAndSet((int)((newTemp - getAvgTemp()) * 100));
                    }
                }    
                tempList[tempListPos] = newTemp;
                tempListPos++;
                if (tempListPos == 30) {
                    tempListPos = 0;
                }
            }
            if (testFrame.isVisible()){
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                    testFrame.getTempField().setText(numberFormat.format(currentTemp.get() / 100d) + " °C");
                    testFrame.getDeltaTempField().setText("+/- " + numberFormat.format(deltaTemp.get() / 100d) + " °C");
                    }
                });
            }
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemperaturWatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private double getAvgTemp() {
        double sum = 0d;
        Boolean hasChanged = false;
        for (int i = 0; i < 30 ; i++) {
            sum += tempList[i];
            if (i < 29) {
                if (tempList[i] != tempList[i + 1]) hasChanged = true;
            }    
        }
        if (hasChanged) {
           return sum / 30d;
        } else {
            buzzer.beep();
            return 0d;
        }
    }
    
}
