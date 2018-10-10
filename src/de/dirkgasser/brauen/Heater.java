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
 * Java class to handle a GPIO pin of Raspberry Pi as Heater <br>
 * can also be used to switch on or off any pin <br>
 * Pin is taken as Address, so column "wPI" of "GPIO readall" command <br>
 *  
 * @author Dirk Gasser
 * @version 1.0
 */
public class Heater {
    private GpioController gpio;
    private GpioPinDigitalOutput pinoutd;
    private int pin;
    
/**
 * @param pin GPIO number, column "wPI" of "GPIO readall" command
*/   
    public Heater (int pin) {
        this.pin = pin;
        gpio = GpioFactory.getInstance();   
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
        pinoutd.low();
    }
/**
 * @param pin GPIO number, column "wPI" of "GPIO readall" command
 * @param gpio GpioFactory instance  
*/   
    public Heater (int pin, GpioController gpio) {
        this.pin = pin;
        this.gpio = gpio;  
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        pinoutd.low();
    }

 /**
 * Set heater on 
*/   
    public void setOn() {
        pinoutd.high();                               
    }
/**
 * Set heater off 
*/   
    public void setOff() {
        pinoutd.low();
    }
/**
 * get status of  heater 
 * @return isOn true if heater is on 
*/   

    public boolean isOn() {
        return pinoutd.getState().isHigh();
    }
}
