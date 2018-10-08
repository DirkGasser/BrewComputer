/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* GPIO readall:
+-----+-----+---------+------+---+---Pi 3---+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 |     |     |    3.3v |      |   |  1 || 2  |   |      | 5v      |     |     |
 |   2 |   8 |   SDA.1 |   IN | 1 |  3 || 4  |   |      | 5v      |     |     |
 |   3 |   9 |   SCL.1 |  OUT | 0 |  5 || 6  |   |      | 0v      |     |     |
 |   4 |   7 | GPIO. 7 |   IN | 0 |  7 || 8  | 0 | IN   | TxD     | 15  | 14  |
 |     |     |      0v |      |   |  9 || 10 | 1 | IN   | RxD     | 16  | 15  |
 |  17 |   0 | GPIO. 0 |   IN | 0 | 11 || 12 | 0 | IN   | GPIO. 1 | 1   | 18  |
 |  27 |   2 | GPIO. 2 |   IN | 0 | 13 || 14 |   |      | 0v      |     |     |
 |  22 |   3 | GPIO. 3 |   IN | 0 | 15 || 16 | 0 | IN   | GPIO. 4 | 4   | 23  |
 |     |     |    3.3v |      |   | 17 || 18 | 0 | IN   | GPIO. 5 | 5   | 24  |
 |  10 |  12 |    MOSI |   IN | 0 | 19 || 20 |   |      | 0v      |     |     |
 |   9 |  13 |    MISO |   IN | 0 | 21 || 22 | 0 | IN   | GPIO. 6 | 6   | 25  |
 |  11 |  14 |    SCLK |   IN | 0 | 23 || 24 | 1 | IN   | CE0     | 10  | 8   |
 |     |     |      0v |      |   | 25 || 26 | 1 | IN   | CE1     | 11  | 7   |
 |   0 |  30 |   SDA.0 |   IN | 1 | 27 || 28 | 1 | IN   | SCL.0   | 31  | 1   |
 |   5 |  21 | GPIO.21 |   IN | 1 | 29 || 30 |   |      | 0v      |     |     |
 |   6 |  22 | GPIO.22 |   IN | 1 | 31 || 32 | 0 | IN   | GPIO.26 | 26  | 12  |
 |  13 |  23 | GPIO.23 |   IN | 0 | 33 || 34 |   |      | 0v      |     |     |
 |  19 |  24 | GPIO.24 |   IN | 0 | 35 || 36 | 0 | IN   | GPIO.27 | 27  | 16  |
 |  26 |  25 | GPIO.25 |  OUT | 0 | 37 || 38 | 0 | IN   | GPIO.28 | 28  | 20  |
 |     |     |      0v |      |   | 39 || 40 | 0 | IN   | GPIO.29 | 29  | 21  |
 +-----+-----+---------+------+---+----++----+---+------+---------+-----+-----+
 | BCM | wPi |   Name  | Mode | V | Physical | V | Mode | Name    | wPi | BCM |
 +-----+-----+---------+------+---+---Pi 3---+---+------+---------+-----+-----+
*/
package de.dirkgasser.brauen;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.GpioPinOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;

/**
 * Java class to handle a pump (or any electric motor) connected to a Raspberry Pi <br>
 * via a L298 bridge driver <br>
 * L298 inputs: <br>
 * <br>
 *  <table summary="L298 functions" border="1px solid black">
 *  <tr>
 *  <th>in1</th>
 *  <th>in2</th>
 *  <th>ena</th>
 *  <th>function</th>
 *  </tr>
 *  <tr>
 *  <th>low </th>
 *  <th>low </th>
 *  <th>low</th>
 *  <th>off</th>
 *  </tr>
 *  <tr>
 *  <th>low </th>
 *  <th>low </th>
 *  <th>high</th>
 *  <th>fast stop</th>
 *  </tr>
 *  <tr>
 *  <th>high </th>
 *  <th>low </th>
 *  <th>high</th>
 *  <th>forward</th>
 *  </tr>
 *  <tr>
 *  <th>low </th>
 *  <th>high </th>
 *  <th>high</th>
 *  <th>backward</th>
 *  </tr>
 *  </table> 
 * Pin is taken as Address, so column "wPI" of "GPIO readall" command <br>
 * Class only supports soft PWM. So hart PWM gpio PIN can't be used  
 * 
 * @author Dirk Gasser
 * @version 1.0
 *
 */
public class Pump {
    private GpioController gpio;
    private GpioPinDigitalOutput pinoutena;
    private GpioPinDigitalOutput pinout1;
    private GpioPinDigitalOutput pinout2;
    private GpioPinPwmOutput pinoutpwm;
    private Boolean pwm;
    private int pwmRate;
    private int pinena;
    private int out1;
    private int out2;
    private String status;
    
/**
 * @param pinena GPIO wPI number to be connected with ENA of L298
 * @param out1 GPIO wPI number to be connected with IN1 of L298
 * @param out2 GPIO wPI number to be connected with IN2 of L298
 * @param gpio GpioFactory instance 
*/   

    public Pump (int pinena, int out1, int out2, GpioController gpio) {
    // ena = 31
    // in1 = 15
    // in2 = 16    
        this.pinena = pinena;
        this.out1 = out1;
        this.out2 = out2;
        this.gpio = gpio;  
        pinoutena = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinena));
        pinoutena.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
        pwm = false;
        pwmRate = 100;
        pinout1 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(out1));
        pinout1.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
        pinout2 = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(out2));
        pinout2.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        pinout1.low();
        pinout2.low();
        pinoutena.low();
        status = "stop";
    }
    
/**
 * Start pump (motor) with latest PWM rate (default = 100%)
*/     
    public void setOn() {
        if (pwm) {
            pinoutpwm.setPwm(pwmRate);
        } else { 
            if (pwmRate != 100) {
                setRate(pwmRate);
            } else {
                pinoutena.high();
            }    
        }
        pinout1.high();
        pinout2.low();
    }

/**
 * reverse pump (motor) direction to backwards with latest PWM rate (default = 100%)
*/    
    public void setBackward() {
        if (pwm) {
            pinoutpwm.setPwm(pwmRate);
        } else {
            pinoutena.high();
        }
        pinout2.high();
        pinout1.low();
    }
    
/**
 * stop pump (motor) with normal stop 
*/    
    public void setOff() {
        if (pwm) {
            gpio.unprovisionPin(pinoutpwm);
            pinoutena = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinena));
            pinoutena.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
            pwm = false;
        } 
        pinoutena.low();
        pinout1.low();
        pinout2.low();
    }
    
/**
 * Set speed of pump (motor) via PWM rate <br>
 * if pump is not yet started, rate will be taken for next start
 * @param rate PWM soft rate for PIN "pinena", so ENA pin of L298
*/  
    public void setRate(int rate) {
        if (!pwm) {
            gpio.unprovisionPin(pinoutena);
            pinoutpwm = gpio.provisionSoftPwmOutputPin(RaspiPin.getPinByAddress(pinena));
            pinoutpwm.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF); 
            pwm = true;
        } 
        pinoutpwm.setPwm(rate);  
    }
    
/**
 * incriease speed of pump (motor)<br>
 * if pump is not yet started, rate will be taken for next start
 * @param increase offset of current PWM rate for PIN "pinena", so ENA pin of L298
*/  
    public void increaseRate(int increase){
        if (pwmRate + increase <= 100) {
            pwmRate += increase;
            if (pinout1.getState().isHigh() || pinout2.getState().isHigh()) {
                setRate(pwmRate);        
            }
        }
    }
/**
 * decrease speed of pump (motor)<br>
 * if pump is not yet started, rate will be taken for next start
 * @param decrease - offset of current PWM rate for PIN "pinena", so ENA pin of L298
*/  
    public void decreaseRate(int decrease){
        if (pwmRate - decrease >= 0) {
            pwmRate -= decrease;
            if (pinout1.getState().isHigh() || pinout2.getState().isHigh()) {
                setRate(pwmRate);    
            }
        }
    }
    
/**
 * return currently used PWM rate of pump
 * @return getRate currently used PWM rate of pump
*/  
    public int getRate(){
        return pwmRate;
    }

/**
 * return true if pump is currently on
 * @return isOn true if pump is currently on
*/  
    public boolean isOn() {
        return pinout1.getState().isHigh();
    }
    
    public void shutdown() {
        this.setOff();
        gpio.shutdown();
    }
    
}
