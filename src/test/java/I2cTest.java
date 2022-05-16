import dragonmove.config.Config;
import dragonmove.i2c.I2CService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class I2cTest {

    Config config = ConfigRead.readConfig();

    @Test
    public void I2cConnectTest() {
        I2CService i2CService = new I2CService(config);
        i2CService.init(50);
        assertEquals(false, i2CService.isDemoMode(), "I2C connection successfull");
    }


    @Test
    public void i2cServireadWriteTest() {
        I2CService i2CService = new I2CService(config);
        if (i2CService.isDemoMode()) {
            fail("No I2C device available");
            return;
        }
        i2CService.init(50);
        i2CService.writeSingleLed(10, 20);
        i2CService.writeSingleLed(11, 2000);
        i2CService.dumpPCA9685();

        int readedValue = i2CService.readSingleLed(10);
        System.out.println("Read LED 10 value is " + readedValue);
        assertEquals(20, readedValue, "Read Write test LED 10 successfull");

        readedValue = i2CService.readSingleLed(11);
        System.out.println("Read LED 11 value is " + readedValue);
        assertEquals(2000, readedValue, "Read Write test LED 11 successfull");
    }


    @Test
    public void servoMoveTest() {
        I2CService i2CService = new I2CService(config);
        if (i2CService.isDemoMode()) {
            fail("No I2C device available");
            return;
        }
        i2CService.init(50);
        for (int servo = 0; servo < 15; servo++) {
            System.out.println("Servo "+servo);
            for (int move = 150; move < 500; move += 5) {

                i2CService.writeSingleLed(servo, move);
            }
            delay(30);

            for (int move = 500; move > 150; move -= 5) {

                i2CService.writeSingleLed(servo, move);
            }
            delay(30);
        }
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
