package it.ennova.rxwifi.internals;

public enum WifiFrequency {
    LOW(0, "2.4 GHz"),
    HIGH(1, "5 GHz"),
    UNKNOWN(2, "N/A");

    private final int level;
    private final String description;

    WifiFrequency(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public boolean is24GHz(){
        return LOW.level == level;
    }

    public boolean is5GHz(){
        return HIGH.level == level;
    }

    @Override
    public String toString() {
        return description;
    }
}
