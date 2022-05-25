package dragonmove.calibrate;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import dragonmove.config.Config;

public class MainPanel extends BasePanel {

    Label interval = new Label("");
    Label movementDir = new Label("");
    Button configButton = new Button("Config");
    Button i2cButton = new Button("I2C");
    Button servoButton = new Button("Servo");
    Config config;

    public MainPanel(TerminalSize terminalSize, Config config){

        super("Main Panel",2,terminalSize);
        this.config = config;

        interval.setText(""+config.getInterval());
        movementDir.setText(config.getDataDir());

        panel.inText("Interval in mS ").inComponent(interval);
        panel.inText("Movement Directory ").inComponent(movementDir);
        panel.inSpace().inSpace();
        panel.inComponent(configButton).inSpace();
        panel.inComponent(i2cButton).inSpace();
        panel.inComponent(servoButton).inSpace().inSpace().inSpace();

        servoButton.addListener(button -> openServoWindow());
        i2cButton.addListener(button -> openI2CWindow(config));
    }

    private void openI2CWindow(Config config) {
        getTextGUI().addWindowAndWait(new I2CPanel(getTextGUI().getScreen().getTerminalSize(),config));
    }

    private void openServoWindow(){
        getTextGUI().addWindowAndWait(new ServoPanel(getTextGUI().getScreen().getTerminalSize(),config));
    }

}
