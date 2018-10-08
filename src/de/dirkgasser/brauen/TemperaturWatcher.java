/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import static de.dirkgasser.brauen.BrewComputerMain.brewframe;
import static de.dirkgasser.brauen.BrewComputerMain.currentTemp;
import static de.dirkgasser.brauen.BrewComputerMain.deltaTemp;
import static de.dirkgasser.brauen.BrewComputerMain.isAlive;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import static de.dirkgasser.brauen.BrewComputerMain.testFrame;

/**
 *
 * @author pi
 */
public class TemperaturWatcher implements Runnable {
    private TemperaturSensor tempSensor;
    private JTextField display;
    private NumberFormat numberFormat;
    double newTemp;
    double[] tempList = new double[30];
    Integer tempListPos;
    
    
    public TemperaturWatcher (TemperaturSensor tempSensor) {
        this.tempSensor = tempSensor;
        numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);
        ((DecimalFormat) numberFormat).applyPattern("###.#"); 
        tempListPos = 0;
        for (int i=0;i<30;i++) {
            tempList[i] = 0;
        }
        deltaTemp = 0d;
        currentTemp = 0d;
    }
    
    @Override
    public void run() {
        while (isAlive) { 
            newTemp = tempSensor.getTemp();
            if (newTemp < 120 && newTemp > -20) {
                currentTemp = newTemp;
                if (tempList[tempListPos] != 0) {
                    if (newTemp < tempList[tempListPos]) {
                        deltaTemp = (newTemp - tempList[tempListPos]) * 0.1;
                    } else if (newTemp > 85) {
                        deltaTemp = (newTemp - tempList[tempListPos]) * 0.2;
                    } else {
                        deltaTemp = (newTemp - tempList[tempListPos]) * 0.6;
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
                    testFrame.getTempField().setText(numberFormat.format(currentTemp) + " °C");
                    testFrame.getDeltaTempField().setText("+/- " + numberFormat.format(deltaTemp) + " °C");
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
    
}
