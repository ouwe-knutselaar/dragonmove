import dragonmove.config.Config;
import dragonmove.i2c.I2CService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class I2Ctest {

    Config config = ConfigRead.readConfig();

    @Test
    public void I2cConnect(){
        I2CService i2CService = new I2CService(config);
        assertEquals(false,i2CService.isDemoMode());
    }

}
