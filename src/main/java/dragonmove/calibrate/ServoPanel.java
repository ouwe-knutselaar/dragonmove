package dragonmove.calibrate;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import dragonmove.config.Config;
import dragonmove.config.Servo;

import java.util.regex.Pattern;

public class ServoPanel extends BasePanel {

    TextBox minVal = new TextBox(new TerminalSize(10,1));
    TextBox maxVal = new TextBox(new TerminalSize(10,1));
    TextBox restVal = new TextBox(new TerminalSize(10,1));
    RadioBoxList<String> servoList = new RadioBoxList<>();
    RadioBoxList<String> actionList = new RadioBoxList<>();
    Pattern numberPattern = Pattern.compile("[0-9]*");
    Button executeAction = new Button("Exec action");
    Config config;

    public ServoPanel(TerminalSize terminalSize, Config config){
        super("Servo Panel",3,terminalSize);
        this.config = config;

        for(int tel=0;tel<15;tel++)servoList.addItem(config.getServoByNumber(tel).getName()+" "+tel);
        servoList.setSize(new TerminalSize(5,10));
        servoList.setSelectedIndex(0);

        actionList.addItem("Min tot Max");
        actionList.addItem("Max tot Min");
        actionList.addItem("To Rest");

        minVal.setValidationPattern(numberPattern);
        maxVal.setValidationPattern(numberPattern);
        restVal.setValidationPattern(numberPattern);

        servoList.addListener(new RadioBoxList.Listener() {
            @Override
            public void onSelectionChanged(int i, int i1) {
                Servo servo = config.getServoByNumber(i);
                maxVal.setText(""+servo.getMin());
                minVal.setText(""+servo.getMax());
                restVal.setText(""+servo.getRest());
            }
        });

        panel.inComponent(minVal.withBorder(Borders.singleLine("minval")));
        panel.inComponent(maxVal.withBorder(Borders.singleLine("maxval")));
        panel.inComponent(restVal.withBorder(Borders.singleLine("default")));
        panel.inComponent(servoList.withBorder(Borders.singleLine("Servo List")));
        panel.inComponent(actionList.withBorder(Borders.singleLine("Action List")));
        panel.inComponent(executeAction.withBorder(Borders.doubleLine("Execute")));

        setComponent(panel);
    }

}
