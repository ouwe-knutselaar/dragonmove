package dragonmove.calibrate;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import dragonmove.Calibrate;
import dragonmove.config.Config;
import dragonmove.i2c.I2CService;

public class ConfigPanel extends BasePanel{

    Button reset = new Button("Reset PCA9685");
    Button saveConfig = new Button("Save config");
    Label message = new Label("no info");

    public ConfigPanel(Config config) {
        super("Config Panel", 4);
        I2CService i2CService = new I2CService(config);

        panel.inText("name   ").inText("Min    ").inText("Default").inText("Max");
        for(int tel=0;tel<16;tel++){
            panel.inText(config.getServoByNumber(tel).getName()).
                    inText(""+config.getServoByNumber(tel).getMin()).
                    inText(""+config.getServoByNumber(tel).getRest()).
                    inText(""+config.getServoByNumber(tel).getMax());
        }
        panel.inText("interval").inText(""+config.getInterval()).inText("dir:").inText(config.getDataDir() );

        reset.addListener(button -> i2CService.reset());
        saveConfig.addListener(button -> saveConfig(config));
        panel.inComponent(reset).inSpace();
        panel.inComponent(saveConfig).inComponent(message);
    }

    public void saveConfig(Config config){
        if(config.saveConfig(Calibrate.getConfigFileStore())){
            message.setText("saved");
        }
        else{
            message.setText("error saving");
        }
    }

}
