package dragonmove.calibrate;

import com.googlecode.lanterna.gui2.*;
import dragonmove.config.Config;

public class MainPanel extends BasePanel {

    Label interval = new Label("");
    Label movementDir = new Label("");
    Button configButton = new Button("Config");
    Button i2cButton = new Button("I2C");
    Button servoButton = new Button("Servo");

    public MainPanel(Config config){
        super("Main Panel",2);

        interval.setText(""+config.getInterval());
        movementDir.setText(config.getDataDir());

        configButton.addListener(button -> getTextGUI().addWindowAndWait(new ConfigPanel(config)));
        servoButton.addListener(button -> getTextGUI().addWindowAndWait(new I2CPanel(config)));
        i2cButton.addListener(button -> getTextGUI().addWindowAndWait(new ServoPanel(config)));

        panel.inText("Interval in mS ").inComponent(interval);
        panel.inText("Movement Directory ").inComponent(movementDir);
        panel.inSpace().inSpace();
        panel.inComponent(configButton).inSpace();
        panel.inComponent(i2cButton).inSpace();
        panel.inComponent(servoButton).inSpace().inSpace().inSpace();

        configButton.takeFocus();
    }


}
