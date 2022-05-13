import dragonmove.config.Config;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DataDirTest {

    @Test
    public void checkConfigFileLoadTest(){
        Config config = ConfigRead.readConfig();
        assertEquals(config.getDataDir(),"/opt/dragon");
        assertEquals(config.getInterval(),2);
    }


}
