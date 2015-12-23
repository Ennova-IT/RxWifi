package it.ennova.rxwifi;

import android.support.annotation.NonNull;

/**
 * This class represents the minimum amount of information needed for showing the different networks
 * on a graph
 */
public class WiFiNetwork {

    private final String SSID;
    private final String BSSID;
    private final String capabilities;
    private final int channel;
    private final int strength;

    public WiFiNetwork(@NonNull String SSID, @NonNull String BSSID, @NonNull String capabilities,
                       int channel, int strength) {

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
        this.channel = channel;
        this.strength = strength;
    }

    @NonNull
    public String getSSID() {
        return SSID;
    }

    @NonNull
    public String getBSSID() {
        return BSSID;
    }

    @NonNull
    public String getCapabilities() {
        return capabilities;
    }

    public int getChannel() {
        return channel;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WiFiNetwork)) {
            return false;
        }

        WiFiNetwork other = (WiFiNetwork) o;
        return other.BSSID.equals(BSSID) && other.SSID.equals(SSID)
                && other.capabilities.equals(capabilities) && other.channel == channel;
    }

    @Override
    public int hashCode() {
        return BSSID.hashCode() + SSID.hashCode() + capabilities.hashCode() + channel;
    }

    @Override
    public String toString() {
        return String.format("%s [%s - %s]\tCh:%d\tStr:%d", SSID, BSSID, capabilities, channel, strength);
    }
}
