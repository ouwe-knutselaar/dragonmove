package dragonmove.calibrate;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import dragonmove.config.Config;
import dragonmove.config.Servo;
import dragonmove.i2c.I2CService;
import java.util.regex.Pattern;

public class ServoPanel extends BasePanel {

    TextBox servoName = new TextBox();
    TextBox minVal = new TextBox();
    TextBox maxVal = new TextBox();
    TextBox restVal = new TextBox();
    TextBox servoStep = new TextBox("1");
    RadioBoxList<String> servoList = new RadioBoxList<>();
    RadioBoxList<String> actionList = new RadioBoxList<>();
    Pattern numberPattern = Pattern.compile("[0-9]*");
    Button executeAction = new Button("Exec action");
    Button stopAction = new Button("Stop action");
    Button updateServo = new Button("update servo");
    Label servoValue = new Label("n/a");
    Label message = new Label("no message");
    Config config;
    I2CService i2CService;
    boolean loop = false;

    public ServoPanel(Config config){
        super("Servo Panel",4);
        this.config = config;
        i2CService = new I2CService(config);
        if(i2CService.isDemoMode())message.setText("I2C Demo mode");

        for(int tel=0;tel<15;tel++)servoList.addItem(config.getServoByNumber(tel).getName()+" "+tel);
        servoList.setCheckedItemIndex(0);
        servoName.setText(config.getServoByNumber(0).getName());
        minVal.setText(""+config.getServoByNumber(0).getMin());
        maxVal.setText(""+config.getServoByNumber(0).getMax());
        restVal.setText(""+config.getServoByNumber(0).getRest());

        actionList.addItem("Move");
        actionList.addItem("To Rest");
        actionList.setCheckedItemIndex(0);

        minVal.setValidationPattern(numberPattern);
        maxVal.setValidationPattern(numberPattern);
        restVal.setValidationPattern(numberPattern);
        servoStep.setValidationPattern(numberPattern);

        executeAction.addListener(button -> servoThread());
        stopAction.addListener(button -> loop=false);
        updateServo.addListener(button -> updateServo(servoList.getCheckedItemIndex()));

        servoList.addListener(new RadioBoxList.Listener() {
            @Override
            public void onSelectionChanged(int i, int i1) {
                minVal.setText(""+config.getServoByNumber(i).getMin());
                maxVal.setText(""+config.getServoByNumber(i).getMax());
                restVal.setText(""+config.getServoByNumber(i).getRest());
                servoName.setText(config.getServoByNumber(i).getName());
            }
        });

        panel.inComponent(servoName.withBorder(Borders.singleLine("name")));
        panel.inComponent(minVal.withBorder(Borders.singleLine("minval")));
        panel.inComponent(maxVal.withBorder(Borders.singleLine("maxval")));
        panel.inComponent(restVal.withBorder(Borders.singleLine("default")));

        panel.inComponent(servoList.withBorder(Borders.singleLine("Servo List")).setPreferredSize(new TerminalSize(20,8)));
        panel.inComponent(actionList.withBorder(Borders.singleLine("Action List")));
        panel.inComponent(servoStep.withBorder(Borders.singleLine("servoStep"))).inSpace();

        panel.inComponent(servoValue.withBorder(Borders.singleLine("servo value")));
        panel.inComponent(message.withBorder(Borders.singleLine("message"))).inSpace().inSpace();

        panel.inComponent(executeAction);
        panel.inComponent(stopAction);
        panel.inComponent(updateServo).inSpace();

        servoList.takeFocus();
    }

    private void updateServo(int servoNumber) {

        Servo servo = config.getServoByNumber(servoNumber);
        servo.setName(servoName.getText());
        servo.setMin(Integer.parseInt(minVal.getText()));
        servo.setMax(Integer.parseInt(maxVal.getText()));
        servo.setRest(Integer.parseInt(restVal.getText()));

        servoList.clearItems();
        for(int tel=0;tel<15;tel++)servoList.addItem(config.getServoByNumber(tel).getName()+" "+tel);
        servoList.setCheckedItemIndex(servoNumber);

    }

    private void servoThread() {
        Thread loopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                servoLoop();
            }
        });
        loopThread.start();
    }


    private void servoLoop(){
        loop = true;
        int servo = servoList.getCheckedItemIndex();
        int min = Integer.parseInt(minVal.getText());
        int max = Integer.parseInt(maxVal.getText());
        int step = Integer.parseInt(servoStep.getText());
        message.setText("Start loop servo "+servo);
        while(loop){
            for(int tel=min;tel<max;tel=tel+step){
                i2CService.writeSingleLed(servo,tel);
                servoValue.setText("Servo "+tel);
                sleep(100);
                if(!loop)break;
            }
            for(int tel=max;tel>min;tel=tel-step){
                i2CService.writeSingleLed(servo,tel);
                servoValue.setText("Servo "+tel);
                sleep(100);
                if(!loop)break;
            }

        }
        message.setText("loop stopped servo "+servo);
    }

    @Override
    protected void closePanel(){
        loop=false;
        close();
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
