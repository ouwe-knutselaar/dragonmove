import dragonmove.config.Config;
import dragonmove.i2c.I2CService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class I2Ctest {

    Config config = ConfigRead.readConfig();

    @Test
    public void I2cConnect(){
        I2CService i2CService = new I2CService(config);
        assertEquals(false,i2CService.isDemoMode(),"I2C connection successfull");
    }


    @Test
    public void i2cServireadWrite(){
        I2CService i2CService = new I2CService(config);
        i2CService.writeSingleLed(10,2000);
        assertEquals(2000,i2CService.readSingleLed(10),"Read Write test successfull");
    }

}
