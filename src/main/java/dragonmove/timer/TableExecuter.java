package dragonmove.timer;

import dragonmove.config.Config;
import dragonmove.data.ServoRecord;
import dragonmove.data.ServoTable;
import dragonmove.data.ServoValue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.Instant;


public class TableExecuter implements Runnable{
    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    private ServoTable servoTable;
    private Config config;
    private boolean isRunning=false;
    private int interval;

    public TableExecuter(ServoTable servoTable, Config config){
        this.servoTable = servoTable;
        this.interval = config.getInterval();
        this.config = config;
        log.setLevel(config.getLevel());
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void stop(){
        log.info("Stop this thread");
        isRunning=false;
    }

    @Override
    public void run() {
        long toEpoch=Instant.now().toEpochMilli();
        log.info("Start table execution");
        isRunning=true;
        int count=0;
        while(isRunning){
            toEpoch+=interval;
            ServoRecord servoRecord=servoTable.getRecord(count);
            for(ServoValue servoValue : servoRecord.getServoValueList()){
                config.getServoByNumber(servoValue.getServonumber()).setCurrentPosition(servoValue.getValue());
            }
            count++;
            if(count>=servoTable.getNumberOfRecord())isRunning=false;
            while(Instant.now().toEpochMilli()<toEpoch){

            }
        }
        isRunning=false;
        log.info("Finished table execution");
    }
}
