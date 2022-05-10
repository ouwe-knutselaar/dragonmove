package dragonmove.config;

import java.util.List;

public class ConfigData {
    private  int interval;
    private String movementDir;
    private List<Servo> servos;
    private String debug;

    public  int getInterval() {
        return interval;
    }

    public  void setInterval(int interval) {
        this.interval = interval;
    }

    public  String getMovementDir() {
        return movementDir;
    }

    public  void setMovementDir(String movementDir) {
        this.movementDir = movementDir;
    }

    public List<Servo> getServos() {
        return servos;
    }

    public void setServos(List<Servo> servos) {
        this.servos = servos;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }
}
