package dragonmove.data;


public class ServoValue {

    private int servonumber;
    private int value;

    public ServoValue(int servonumber,int value){
        this.servonumber=servonumber;
        this.value=value;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getServonumber() {
        return servonumber;
    }

    @Override
    public String toString() {
        return "s:" + servonumber + ":" + value;
    }
}
