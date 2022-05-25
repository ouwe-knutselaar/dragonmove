package dragonmove.config;

import java.util.Random;

public class Servo {

    private String name="noname";
    private int number;
    private int min;
    private int rest;
    private int max;
    private int currentPosition;
    private int newPosition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
        this.currentPosition = rest;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    @Override
    public String toString() {
        return "Servo{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", min=" + min +
                ", rest=" + rest +
                ", max=" + max +
                ", currentPosition=" + currentPosition +
                ", newPosition=" + newPosition +
                '}';
    }

    public int getNewRandomPosition(){
        Random rand = new Random();
        return rand.nextInt(max-min)+min;
    }
}
