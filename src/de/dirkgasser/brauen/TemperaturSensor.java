package de.dirkgasser.brauen;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
/**
 * Reads the temperature from a DS18B20 sensor.<br>
 * DS18B20 connections: <br>
 * red (VCC 3,3 V), yellow (Data), black (GND) <br>
 * W1 interface for BS18B20 must be configured in <br>
 * /boot/config.txt <br>:
 * dtoverlay=w1-gpio,gpiopin=4 <br>
 * @author Dirk Gasser
 * @version 1.0
 */
public class TemperaturSensor {
    /* path to search for devices in filesystem */
    private static String devicesPath = "/sys/bus/w1/devices";

    /* file of the measured values */
    private static String valueFile = "w1_slave";

    /* id of sensor */
    private String id = null;
    private List<String> DS18B20List;
/**
 * Sensor must be connected before instance in created <br>
 * Instance will contain a list of all DS18B20 which are connected
*/    
    public TemperaturSensor() {
        
       DS18B20List = new ArrayList<String>();
       File searchPath = new File(devicesPath);
       if (searchPath.listFiles()!=null) {
         for (File f: searchPath.listFiles()) {
          if (f.isDirectory() && !f.getName().startsWith("w1_bus_master")) 
              if (this.getTemp(f.getName()) > 0) {
                DS18B20List.add(f.getName());
              }
         }
       }
       if (DS18B20List.size() > 0) {
           id = DS18B20List.get(0);
       }
    }
      
    /**
     * Get the sensor id of currently selected sensor
     * @return sensor id from /sys/bus/w1/devices
     */
    public String getId() {
       return id;
    }

    /**
     * Set the sensor id
     * @param id sensor id in /sys/bus/w1/devices
     */
    public void setId(String id) {
       this.id = id;
    }
    
 /**
 * returns a list of all connected DS18B20 sensors
 * @return getList ArrayList with names in /sys/bus/w1/devices
*/  
    public List<String> getList() {
       return DS18B20List;
    }

    private double getTemp(String idin) {
        if (idin==null) {
            return 0;
        } else {
            Path path = FileSystems.getDefault().getPath(devicesPath, idin, valueFile);
            List<String> lines;
            if (!path.toFile().exists()) {
             return 0;
            }
            int attempts = 3;
            boolean crcOK = false;

            while (attempts > 0) {
               try {
                   lines = Files.readAllLines(path);
                   for(String line: lines) {
                       if (line.endsWith("YES")) {
                          crcOK = true;
                        } else if (line.matches(".*t=[0-9-]+") && crcOK) {
                            Double temp = Integer.valueOf(line.substring(line.indexOf("=")+1))/1000.0;
                            if (temp.equals(85d)) return 0; else return temp;
                        }
                    }
                } catch (Exception e) {
                   Logger.getLogger(TemperaturSensor.class.getName()).log(Level.SEVERE, null, e);
                }
                attempts--;
            }
            return 0;
        }
    }
 /**
 * returns the current temperaturs in Celsius of the currently selected sensor
 * if read of temperatur fails 0 is returned
 * @return getTemp current temperaturs in Celsius
 */
    public double getTemp() {
        return getTemp(id);
    }

}

