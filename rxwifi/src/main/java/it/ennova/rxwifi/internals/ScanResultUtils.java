package it.ennova.rxwifi.internals;

import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;

import it.ennova.rxwifi.WiFiNetwork;

/**
 * This class is the one that takes care of converting the given {@link ScanResult} to a new
 * {@link WiFiNetwork}
 */
public class ScanResultUtils {

    private ScanResultUtils () {}

    public static WiFiNetwork toWiFiNetwork(@NonNull ScanResult scanResult) {
        return new WiFiNetwork(scanResult.SSID, scanResult.BSSID, scanResult.capabilities,
                ChannelUtils.fromFrequency(scanResult.frequency), PowerUtils.toPercentage(scanResult));
    }
}
