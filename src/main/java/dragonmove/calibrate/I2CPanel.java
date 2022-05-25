package dragonmove.calibrate;

import com.googlecode.lanterna.TerminalSize;
import dragonmove.config.Config;
import dragonmove.i2c.I2CService;

public class I2CPanel extends BasePanel{



    public I2CPanel( Config config) {
        super("I2C Panel", 3);

        I2CService i2CService = new I2CService(config);
        for(int tel = 0 ;tel < 16;tel++) {
            panel.inText("Servo "+tel).inText(" value is "+ i2CService.readSingleLed(tel)).inSpace();
        }
    }
}
