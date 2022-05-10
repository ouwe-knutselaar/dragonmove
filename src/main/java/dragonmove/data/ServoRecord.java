package dragonmove.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServoRecord {

    private final int RECORDSIZE=16;
    private int recordNumber;
    private int servo=0;

    private ServoValue[] servoValueList = new ServoValue[RECORDSIZE];

    public ServoRecord(int[] valueList){
        for(int count =0; count< valueList.length; count++){
            servoValueList[count]=new ServoValue(count,valueList[count]);
        }
    }

    public ServoRecord(int servoNumber,int value){
        for(int count =0; count< RECORDSIZE; count++){
            servoValueList[count]=new ServoValue(count,0);
        }
        servoValueList[servoNumber].setValue(value);
    }

    public ServoRecord(){
        for(int count =0; count< RECORDSIZE; count++){
            servoValueList[count]=new ServoValue(count,0);
        }
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(servoValueList).forEach(servoValue -> sb.append(servoValue.toString()).append("  "));
        return sb.toString();
    }

    public ServoValue[] getServoValueList() {
        return servoValueList;
    }

    public void setServoValueList(ServoValue[] servoValueList) {
        this.servoValueList = servoValueList;
    }
}
