/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import static de.dirkgasser.brauen.BrewComputerMain.brewframe;
import static de.dirkgasser.brauen.BrewComputerMain.buzzer;
import static de.dirkgasser.brauen.BrewComputerMain.currentTemp;
import static de.dirkgasser.brauen.BrewComputerMain.deltaTemp;
import static de.dirkgasser.brauen.BrewComputerMain.heater;
import static de.dirkgasser.brauen.BrewComputerMain.isAlive;
import static de.dirkgasser.brauen.BrewComputerMain.pump;
import java.awt.Color;
import java.util.HashMap;


/**
 *
 * @author Dirk Gasser
 * @version 1.0
 * The class control the brew process<br>
 * Each brew step follows the following workflow: <br>
 *    - WaitStartConditions<br>
 *    - WaitManualStart (If required for the step)<br>
 *    - WaitforTemp<br>
 *    - Processing<br>
 * Class is started as runnable and starts itself every second<br>
 * Because Swing isn't thread save, loop starts instance itself as invokeLater <br>
 */
public class BrewProcess implements Runnable {

    Instant startTime;
    Instant startOfStep;
    Integer currentStep;
    Instant waitForAction;
    Instant startPause;
    Instant lastTempCheck;
    Long stepOffsetSec;
    Long totalSec;
    Long stepSec;
    Integer stepMin;
    Integer currentState;
    Integer pauseState;
    Integer oldState;
    BrewRecipe brewRecipe;
    HashMap<Integer, String> status = new HashMap<Integer, String>();
    Boolean invoked;
    double deltaTargetTemp;
    Integer buzzerBeep;
    
/**
 * Create instance of brew process 
 * @param brewRecipe instace of BrewReceipe 
 */
    public BrewProcess (BrewRecipe brewRecipe) {
        startTime = null;
        waitForAction = null;
        startOfStep = null;
        currentStep = 0;
        currentState = 0;
        deltaTargetTemp = 0;
        buzzerBeep = 0;
        this.brewRecipe = brewRecipe;
        status.put(1, "nächster Schritt");
        status.put(2, "warten auf manuellen Start");
        status.put(3, "warten auf Starttemperatur");
        status.put(4, "läuft");
        status.put(5, "fertig");
        status.put(6, "Pause");
        invoked = false;
    }
    
    @Override
    public void run() {
        if (startTime == null) {
           invokeLoop(); 
        } else {  
            if (!currentState.equals(5)) {

                oldState = currentState;
                totalSec = Duration.between(startTime, Instant.now()).getSeconds();
                brewframe.setTimeAll(totalSec.intValue());
                brewframe.setTempIs(currentTemp);

                if (currentState.equals(1)) {
                    checkStartConditions();
                    if (brewRecipe.getNumberOfSteps() >= currentStep) {
                        brewframe.setTempToBe(brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur());
                        deltaTargetTemp = 0;
                    }    
                }
                if (currentState.equals(2)) {
                    if (Duration.between(waitForAction, Instant.now()).getSeconds() > 60L
                        && buzzerBeep < 3) {
                        buzzer.beep();
                        buzzerBeep ++;
                        waitForAction = Instant.now();
                    }
                }
                if (currentState.equals(3)) {
                    if (currentTemp + deltaTemp > brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() + deltaTargetTemp) {
                        synchronized(this) {
                            currentState = 4;
                            startOfStep = Instant.now();
                            brewframe.setStepColor(currentStep, Color.green);
                            lastTempCheck = Instant.now();
                        }
                    }
                }
                if (currentState.equals(4)) {
                    stepSec = Duration.between(startOfStep, Instant.now()).getSeconds() + stepOffsetSec;
                    brewframe.setTimeStep(stepSec.intValue());
                    stepMin = (int)(stepSec / 60);
                    if (stepMin >= brewRecipe.getBrewStepbyPosition(currentStep).getDuration()) { 
                        synchronized(this) {
                            currentState = 1;
                            currentStep += 1;
                            stepOffsetSec = 0l;
                            brewframe.setHeater("OFF");
                            brewframe.setPump("OFF");
                            brewframe.setTimeStep(0);
                        }
                    } else {
                        if (heater.isOn() && 
                            currentTemp + deltaTemp > brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() + deltaTargetTemp) {
                            lastTempCheck = Instant.now();
                            brewframe.setHeater("OFF");
                        } else if (!heater.isOn() &&
                                   currentTemp + deltaTemp < brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() + deltaTargetTemp) {
                            synchronized(this) {
                                brewframe.setHeater("ON");
                                if (brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() < 80) {
                                    brewframe.setPump("ON");
                                }
                                lastTempCheck = Instant.now();
                            }
                        }
                        if (!heater.isOn() && pump.isOn() &&
                            Duration.between(lastTempCheck, Instant.now()).getSeconds() > 120) {
                            lastTempCheck = Instant.now();
                            brewframe.setPump("OFF");
                        }
                        if (!heater.isOn() && !pump.isOn() &&
                            brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() < 80 &&    
                            Duration.between(lastTempCheck, Instant.now()).getSeconds() > 600) {
                            lastTempCheck = Instant.now();
                            brewframe.setPump("ON");
                        }
                    }
                }
                if (!oldState.equals(currentState)) {
                    brewframe.setStatus(status.get(currentState));
                }


            } else {
                brewframe.setStatus(status.get(5));
                brewframe.setPump("OFF");
                brewframe.setHeater("OFF");
                brewframe.setTempToBe(0);  
                brewframe.setFinished();
            }
            invoked = false;
        }
    }
    
    
    private void checkStartConditions() {
        if (brewRecipe.getNumberOfSteps() < currentStep) {
            currentState = 5;
            brewframe.setStepColor(currentStep, Color.white);
        } else {
            if (currentStep  <  brewRecipe.getNumberOfSteps()) {
                if (currentStep == 1) {
                    brewframe.setStepsinFrame(currentStep);
                } else {
                    brewframe.setStepsinFrame(currentStep - 1);
                }
            }
             synchronized(this) {
                if (brewRecipe.getBrewStepbyPosition(currentStep).getManual()) {
                    currentState = 2;
                    buzzer.beep();
                    buzzerBeep = 1;
                    brewframe.jbConfirmSetRed();
                    waitForAction = Instant.now();
                } else {
                    currentState = 3;
                    brewframe.setHeater("ON");
                    if (brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() < 80) {
                        brewframe.setPump("ON");
                    }
                }
                brewframe.setStepColor(currentStep, Color.yellow);
            }
        }
    }
    
/**
 * Stop brew process
 */
    public void stop() {
        currentState = 5;
    }
/**
 * pause brew process
*/
    public void pause() {
        pauseState = currentState;
        currentState = 6;
        startPause = Instant.now();
    }
/**
 * re-start brew process after pause
*/    
    public void restart() {
        currentState = pauseState;
        stepOffsetSec = stepOffsetSec - Duration.between(startPause, Instant.now()).getSeconds();
    }
/**
 * add 1 minute to runtime of brewstep 
*/   
    public void plus1min() {
        stepOffsetSec = stepOffsetSec + 60;
    }
/**
 * substract 1 minute to runtime of brewstep 
*/  
    public void minus1min() {
        stepOffsetSec = stepOffsetSec - 60;
    }
/**
 * jump to previous brew step  
*/  
    public void stepBack() {
        if (currentStep > 1) {
            synchronized(this) {
                currentStep += -1;
                stepOffsetSec = 0L;
                currentState = 1;
                brewframe.setPump("OFF");
                brewframe.setHeater("OFF");
                if (currentStep > 1) {
                    brewframe.setStepsinFrame(currentStep - 1);
                }
            }    
        }    
    }
    
/**
 * jump to next brew step  
*/ 
    public void stepForward() {
        if (currentStep < brewRecipe.getNumberOfSteps()) {
            synchronized(this) {
                currentStep += 1;
                stepOffsetSec = 0L;
                currentState = 1;
                brewframe.setPump("OFF");
                brewframe.setHeater("OFF");
                if (currentStep  <  brewRecipe.getNumberOfSteps()) {
                    brewframe.setStepsinFrame(currentStep - 1);
                }
            }    
        }    
    }
/**
 * confirm manual action of brew step  
*/ 

    public void confirm() {
        if (currentState.equals(2)) { 
            synchronized(this) {
                currentState = 3;
                brewframe.setHeater("ON");
                if (brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() < 80) {
                    brewframe.setPump("ON");
                }
                brewframe.setStatus(status.get(currentState));
            }
        }
    }
/**
* increment target temperature of brew step by 0.1 C   
*/     
    public void incDeltaTargetTemp(){
        synchronized(this) {
            deltaTargetTemp += 0.1; 
            brewframe.setTempToBe(brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() + deltaTargetTemp);
        }
    }
/**
* decrement target temperature of brew step by 0.1 C   
*/   

    public void decDeltaTargetTemp(){
        synchronized(this) {
            deltaTargetTemp += -0.1;  
            brewframe.setTempToBe(brewRecipe.getBrewStepbyPosition(currentStep).getTemperatur() + deltaTargetTemp);
        }
    }
    
    protected void invokeLoop() {
        startTime = Instant.now();
        currentStep = 1;
        stepOffsetSec = 0L;
        currentState = 1;
        
         while (!currentState.equals(5) && isAlive) {
            if (!invoked) {
                java.awt.EventQueue.invokeLater(this);
                invoked = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BrewProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         java.awt.EventQueue.invokeLater(this); //to stop heater and pump
    }
    
}
