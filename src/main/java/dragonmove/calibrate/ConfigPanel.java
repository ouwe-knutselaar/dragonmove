package dragonmove.calibrate;

import com.googlecode.lanterna.gui2.Button;
import dragonmove.config.Config;
import dragonmove.i2c.I2CService;


public class ConfigPanel extends BasePanel{

    Button reset = new Button("Reset PCA9685");
    Button saveConfig = new Button("Save config");

    public ConfigPanel(Config config) {
        super("Config Panel", 2);
        I2CService i2CService = new I2CService(config);

        reset.addListener(button -> i2CService.reset());

        panel.inComponent(reset).inSpace();
        panel.inComponent(saveConfig).inSpace();

    }
}
