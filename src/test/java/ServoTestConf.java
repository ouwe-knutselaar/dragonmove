class ServotestConf{
    int delay;
    int min;
    int max;
    int step;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }


    @Override
    public String toString() {
        return "ServotestConf{" +
                "delay=" + delay +
                ", min=" + min +
                ", max=" + max +
                ", step=" + step +
                '}';
    }
}

