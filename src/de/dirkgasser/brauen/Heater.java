/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dirkgasser.brauen;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pi
 */
public class Heater {
    private GpioController gpio;
    private GpioPinDigitalOutput pinoutd;
    private int pin;
    
    public Heater (int pin) {
        this.pin = pin;
        gpio = GpioFactory.getInstance();   
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
        pinoutd.low();
    }
    public Heater (int pin, GpioController gpio) {
        this.pin = pin;
        this.gpio = gpio;  
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        pinoutd.low();
    }
    
    public void setOn() {
        pinoutd.high();                               
    }
    public void setOff() {
        pinoutd.low();
    }
    public boolean isOn() {
        return pinoutd.getState().isHigh();
    }
}
