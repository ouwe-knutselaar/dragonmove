import dragonmove.config.Config;
import dragonmove.i2c.I2CService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class I2cTest {

    Config config = ConfigRead.readConfig();

    @Test
    public void I2cConnectTest(){
        I2CService i2CService = new I2CService(config);
        i2CService.init(50);
        assertEquals(false,i2CService.isDemoMode(),"I2C connection successfull");
    }


    @Test
    public void i2cServireadWriteTest(){
        I2CService i2CService = new I2CService(config);
        if(i2CService.isDemoMode()){
            fail("No I2C device available");
            return;
        }
        i2CService.init(50);
        i2CService.writeSingleLed(10,20);
        i2CService.dumpPCA9685();
        int readedValue = i2CService.readSingleLed(10);
        System.out.println("Read LED value is "+readedValue);
        assertEquals(2000,readedValue,"Read Write test successfull");
    }

}
