package dragonmove.i2c;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import dragonmove.config.Config;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Arrays;


public class I2CService {

	private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

	private static final int PCAADDR = 0x40;
	private static final int MODE1 = 0x00;
	private static final int PRESCALE = 0xFE;
	private static final int SLEEP = 0b00010000;
	private static final int AI = 0b00100000;
	private static final int LEDBASE = 0x06;
	private static final int[] LEDBASELIST = { 6, 10, 14, 18, 22, 26, 30, 34, 38, 42, 46, 50, 54, 58, 62, 66 };
	private static final int[] FULLZERO={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private I2CDevice i2cdev;
	private boolean demoMode=false;
	private static final int SERVOMIN=0;
	private static final int SERVOMAX=15;

	public I2CService(Config config) {
		log.setLevel(config.getLevel());
		log.info("Make I2CService");
		init(1000/config.getInterval());
	}

	
	public void init(int frequency) {
		log.info("Init the PCA9685");

		try {
			I2CBus i2cbus = I2CFactory.getInstance(I2CBus.BUS_1);
			i2cdev = i2cbus.getDevice(PCAADDR);

			int settingsMode1 = i2cdev.read(MODE1);
			log.info("Current settings MODE1 is B" + Integer.toBinaryString(settingsMode1));

			settingsMode1 = settingsMode1 & 0xEF | AI;
			log.info("Enable auto increment B" + Integer.toBinaryString(settingsMode1));
			i2cdev.write(MODE1, (byte) settingsMode1);

			setFrequency(frequency);
			log.info("Init done");
			
		} catch (UnsupportedBusNumberException e) {
			log.info("UnsupportedBusNumberException switch to demo mode "+e.getMessage());
			demoMode = true;
		} catch (IOException e) {
			log.error("IO Exception, switch to demo mode "+e.getMessage());
			e.printStackTrace();
			demoMode = true;
		} 
	}

	
	public void setFrequency(int frequency) {
		try {
			log.info("Set the frequencyof the  PCA9685 on " + frequency + "Hz");
			int prescale = (25_000_000 / (4096 * frequency)) - 1;
			log.info("Prescale set on " + prescale);
			if (demoMode)return;
			int settingsMode1 = i2cdev.read(MODE1);
			i2cdev.write(MODE1, (byte) (settingsMode1 | SLEEP));
			i2cdev.write(PRESCALE, (byte) prescale);
			i2cdev.write(MODE1, (byte) (settingsMode1 & 0xEF));
			Thread.sleep(500);
		} catch (IOException e) {
			log.error("Error cannot write to the I2C device");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	
	public void reset() {
		try {
			log.info("Reset the PCA9685");
			if(demoMode)return;
			int settingsMode1 = i2cdev.read(MODE1);
			i2cdev.write(MODE1, (byte) (settingsMode1 | 0x80));
			writeAllServos(FULLZERO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeSingleLed(int lednumber, int data) {
		try {
			log.debug("servo "+lednumber+" has value "+data);
			if(demoMode)return;
			if(lednumber < SERVOMIN)throw new IllegalArgumentException("led or servo number cannot be less then 0");
			if(lednumber > SERVOMAX)throw new IllegalArgumentException("led or servo number cannot be more then 15");
			if(data < 0)throw new IllegalArgumentException("led or servo value cannot be more then 0");
			if(data > 4095)throw new IllegalArgumentException("led or servo value cannot be more then 4095");

        	byte[] result=new byte[4];
        	result[1] = (byte) ((data & 0xFF000000) >> 24);		// LED ON_L
        	result[0] = (byte) ((data & 0x00FF0000) >> 16);		// LED ON_H
        	result[3] = (byte) ((data & 0x0000FF00) >> 8);		// LED OFF_L
        	result[2] = (byte) ((data & 0x000000FF) >> 0);		// LED OFF_H

			i2cdev.write(LEDBASELIST[lednumber],result);
		} catch (IOException e) {
			log.error("Error "+e.getMessage());
		}
	}


	public void writeAllServos(int[] valueList) {
		try {
			log.debug("Write valuelist tot the I2C device " + Arrays.toString(valueList));
			if (demoMode)return;
			byte[] byteValueList = new byte[valueList.length * 4];
			for (int tel = 0; tel < valueList.length; tel++) {
				if(valueList[tel] == 0) continue;					// Do not execute 0 values
				byte[] result = intToBytes(valueList[tel]);
				byteValueList[tel * 4] = result[0];
				byteValueList[1 + tel * 4] = result[1];
				byteValueList[2 + tel * 4] = result[2];
				byteValueList[3 + tel * 4] = result[3];
			}
			i2cdev.write(LEDBASE, byteValueList);
		} catch (IOException e) {
			log.error("Error cannot write to the I2C device");
			e.printStackTrace();
		}
	}
	

	private static byte[] intToBytes(final int data) {
		return new byte[] { (byte) ((data >> 16) & 0xff), 
							(byte) ((data >> 24) & 0xff), 
							(byte) ((data >> 0) & 0xff),
							(byte) ((data >> 8) & 0xff), };
	}

	public void dumpPCA9685(){
		log.info("dump PCA9685");

		byte[] result=new byte[256];
		try {
			i2cdev.read(0,result,0,256);
			log.info("MODE1      "+Integer.toBinaryString(result[0])+" "+result[0]);
			log.info("MODE2      "+Integer.toBinaryString(result[1])+" "+result[1]);
			log.info("SUBADR1    "+Integer.toBinaryString(result[2])+" "+result[2]);
			log.info("SUBADR2    "+Integer.toBinaryString(result[3])+" "+result[3]);
			log.info("SUBADR3    "+Integer.toBinaryString(result[4])+" "+result[4]);
			log.info("ALLCALLADR "+Integer.toBinaryString(result[5])+" "+result[5]);

			for(int tel =0 ;tel <16 ;tel++){
				log.info(" LED"+tel+"\t"+result[6+(tel*4)]+" "+result[7+(tel*4)]+" "+result[8+(tel*4)]+" "+result[9+(tel*4)]+" ");
			}

		} catch (IOException e) {
			log.error("Error during read "+e.getMessage());
			e.printStackTrace();
		}
	}

	public int readSingleLed(int ledNumber){
		try {
			byte valueLow = (byte)i2cdev.read(6+(4*ledNumber));
			byte valueHigh = (byte) i2cdev.read(7+(4*ledNumber));
			return (256*valueHigh) + valueLow;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean isDemoMode() {
		return demoMode;
	}
}
