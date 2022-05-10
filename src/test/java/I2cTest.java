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
        i2CService.init(50);
        i2CService.writeSingleLed(10,2000);
        assertEquals(2000,i2CService.readSingleLed(10),"Read Write test successfull");
    }

}
