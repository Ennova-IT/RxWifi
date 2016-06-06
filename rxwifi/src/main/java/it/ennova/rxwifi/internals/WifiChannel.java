package it.ennova.rxwifi.internals;

class WifiChannel {

    public final WifiFrequency frequency;
    public final int channel;

    public WifiChannel(WifiFrequency frequency, int channel) {
        this.frequency = frequency;
        this.channel = channel;
    }
}
