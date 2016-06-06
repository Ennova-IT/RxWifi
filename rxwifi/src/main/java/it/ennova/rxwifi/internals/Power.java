package it.ennova.rxwifi.internals;

class Power {

    private final int percentage;
    private final int level;

    protected Power(int percentage, int level) {
        this.percentage = percentage;
        this.level = level;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getLevel() {
        return level;
    }
}
