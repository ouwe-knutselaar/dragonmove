import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dragonmove.config.Config;
import dragonmove.config.ConfigData;
import dragonmove.i2c.I2CService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

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
    public void servoMoveTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ServotestConf configServoData=objectMapper.readValue(Paths.get(ConfigRead.readServoConfig()).toFile(), ServotestConf.class);
        System.out.println(configServoData);
        I2CService i2CService = new I2CService(config);
        if (i2CService.isDemoMode()) {
            fail("No I2C device available");
            return;
        }
        i2CService.init(50);
        for (int servo = 0; servo < 15; servo++) {
            System.out.println("Servo "+servo);
            for (int move = configServoData.getMin(); move < configServoData.getMax(); move +=configServoData.getStep()) {

                i2CService.writeSingleLed(servo, move);
            }
            delay(configServoData.getDelay());

            for (int move = configServoData.getMax(); move > configServoData.getMin(); move -= configServoData.getStep()) {

                i2CService.writeSingleLed(servo, move);
            }
            delay(configServoData.getDelay());
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
