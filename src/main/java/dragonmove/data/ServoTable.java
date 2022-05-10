package dragonmove.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Log
public class ServoTable {

    private int currentRecordNumber;

    private List<ServoRecord> servoRecordList = new ArrayList<>();

    public void addRecord(int [] valueList){
        ServoRecord newServoRecord = new ServoRecord(valueList);
        newServoRecord.setRecordNumber(currentRecordNumber);
        servoRecordList.add(newServoRecord);
        currentRecordNumber++;
    }

    public void modServoFromArray(int servo,int []valueArray){
        for(int tel=0;tel<valueArray.length;tel++){
            modServo(tel,servo,valueArray[tel]);
        }
    }

    public void modServo(int recordNumber,int servoNumber,int value){
        if(recordNumber>=servoRecordList.size()){
            for(int count=servoRecordList.size()-1;count<recordNumber;count++)servoRecordList.add(new ServoRecord());
        }
        ServoRecord servoRecord = servoRecordList.get(recordNumber);
        servoRecord.getServoValueList()[servoNumber].setValue(value);
        servoRecordList.set(recordNumber,servoRecord);
    }

    public int getNumberOfRecord(){
        return servoRecordList.size();
    }

    public ServoRecord getRecord(int recordNumber){
        return servoRecordList.get(recordNumber);
    }

    public void dumpTable(){
        servoRecordList.forEach(record ->log.info(record.toString()));
    }

    public void saveTable(String filename) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Files.write(Paths.get(filename),
                        objectMapper.writeValueAsString(servoRecordList).getBytes());
            log.info("Servo Table saved to "+filename);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadTable(String loadFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            servoRecordList=objectMapper.readValue(Files.readAllBytes(Paths.get(loadFile)),ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Read "+servoRecordList.size()+" lines from file "+loadFile);
        return servoRecordList.size();
    }

    public int getValueFromSpecificRecord(int record,int valuePosition){
        return servoRecordList.get(record).getServoValueList()[valuePosition].getValue();
    }

}
