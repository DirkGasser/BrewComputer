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
 * Java class to handle a GPIO pin of Raspberry Pi as Buzzer <br>
 * Pin is taken as Address, so column "wPI" of "GPIO readall" command <br>
 * Buzzer beep makes five 300ms beeps 
 * 
 * @author Dirk Gasser
 * @version 1.0
 * 
 */
public class Buzzer {
    private GpioController gpio;
    private GpioPinDigitalOutput pinoutd;
    private int pin;
 
/**
 * creator with ony PIN
 * @param pin GPIO number, column "wPI" of "GPIO readall" command
*/   
    public Buzzer (int pin) {
        this.pin = pin;
        gpio = GpioFactory.getInstance();   
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
    }
/**
 * Creator which PIN and GpioFactory
 * @param pin GPIO number, column "wPI" of "GPIO readall" command
 * @param gpio GpioFactory instance 
*/
    public Buzzer (int pin, GpioController gpio) {

        this.pin = pin;
        this.gpio = gpio;  
        pinoutd = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pin));
        pinoutd.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
    }
/**
 * let the buzzer beep
 */
    public void beep() {
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        pinoutd.high();                               
                        Thread.sleep(300);   
                        pinoutd.low();
                        Thread.sleep(300);   
                     } catch (InterruptedException ex) {
                         Logger.getLogger(Buzzer.class.getName()).log(Level.SEVERE, null, ex);
                     }
                }
            };
        };
        t.start();
    }
}
