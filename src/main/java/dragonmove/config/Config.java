package dragonmove.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Config {

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
    private  ConfigData configData;
    private List<Servo> servoList = new ArrayList<>();
    private Level level = Level.INFO;

    public Config(String configFile){
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            configData=objectMapper.readValue(Paths.get(configFile).toFile(),ConfigData.class);

            for(int tel=0;tel<16;tel++)servoList.add(new Servo());
            configData.getServos().forEach(servo -> servoList.set(servo.getNumber(),servo));
            //dumpConfig();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public  int getInterval(){
        if(configData==null)throw new IllegalStateException("Config not loaded");
        return configData.getInterval();
    }

    public  String getDataDir(){
        if(configData==null)throw new IllegalStateException("Config not loaded");
        return configData.getMovementDir();
    }

    public Servo getServoByNumber(int number){
        return servoList.get(number);
    }

    public Servo getServoByName(String name){
        return servoList.stream().filter(servo -> servo.getName().equals(name)).findFirst().get();
    }

    public String getDebug(){
        return configData.getDebug();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void dumpConfig(){
        servoList.forEach(servo -> log.info(servo.toString()));
        log.info("Interval: "+configData.getInterval());
        log.info("Movement directory: "+configData.getMovementDir());
        log.info("Debug: "+configData.getDebug());
    }

}
