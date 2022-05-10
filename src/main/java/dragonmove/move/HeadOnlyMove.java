package dragonmove.move;

import dragonmove.config.Config;
import dragonmove.config.Servo;
import dragonmove.data.ServoTable;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class HeadOnlyMove {

    Logger log = LogManager.getLogger("HeadOnlyMove");
    private List<String> servoNamesList=new ArrayList<>();
    private Config config;

    public HeadOnlyMove(Config config){
        this.config = config;
        servoNamesList.add("nekturn");
        servoNamesList.add("nekleft");
        servoNamesList.add("nekright");
        log.setLevel(config.getLevel());
    }

    public List<String> getServoNamesList() {
        return servoNamesList;
    }

    public ServoTable makeNewMovement(){
        log.info("New random head movement");
        int nekturnNewPos = config.getServoByName("nekturn").getNewRandomPosition();
        int nekleftNewPos = config.getServoByName("nekleft").getNewRandomPosition();
        int nekrightNewPos = config.getServoByName("nekright").getNewRandomPosition();
        return makeNewMovement(
                nekturnNewPos,
                nekleftNewPos,
                nekrightNewPos
        );
    }

    public ServoTable makeNewMovement(int newNekturnPos,int newNekleftPos,int newNekrightPos) {
        Servo nekTurnServo = config.getServoByName("nekturn");
        nekTurnServo.setNewPosition(newNekturnPos);
        int []nekturnArray = ServoMove.makeMove(nekTurnServo.getCurrentPosition(),nekTurnServo.getNewPosition(),100);

        Servo nekleftServo = config.getServoByName("nekleft");
        nekleftServo.setNewPosition(newNekleftPos);
        int []nekleftArray = ServoMove.makeMove(nekleftServo.getCurrentPosition(),nekleftServo.getNewPosition(),100);

        Servo nekrightServo = config.getServoByName("nekright");
        nekrightServo.setNewPosition(newNekrightPos);
        int []nekrightArray = ServoMove.makeMove(nekrightServo.getCurrentPosition(),nekrightServo.getNewPosition(),100);

        ServoTable servoTable = new ServoTable();
        servoTable.modServoFromArray(nekTurnServo.getNumber(),nekturnArray);
        servoTable.modServoFromArray(nekleftServo.getNumber(),nekleftArray);
        servoTable.modServoFromArray(nekrightServo.getNumber(),nekrightArray);
        log.info("New head movement");
        log.info("nekturnNewPos:"+newNekturnPos+" nekleftNewPos:"+newNekleftPos+" nekrightNewPos:"+newNekrightPos);

        return servoTable;
    }

    public ServoTable toHomePosition(){
        log.info("Go the the head move home position");
        Servo nekTurnServo = config.getServoByName("nekturn");
        Servo nekleftServo = config.getServoByName("nekleft");
        Servo nekrightServo = config.getServoByName("nekright");
        return makeNewMovement(
                nekTurnServo.getRest(),
                nekleftServo.getRest(),
                nekrightServo.getRest()
                );
    }
}
