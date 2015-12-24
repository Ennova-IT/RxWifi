package it.ennova.rxwifi.internals;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

/**
 * This class is the one used in order to convert the signal power to a percentage that can be used
 * for representing the various aspects
 */
class PowerUtils {

    private PowerUtils() {}

    private static final int SCALE = 100;

    public static Power from(@NonNull ScanResult scanResult) {
        return new Power(toPercentage(scanResult.level), WifiManager.calculateSignalLevel(scanResult.level, SCALE));
    }

    public static int toPercentage(@NonNull ScanResult scanResult) {
        return toPercentage(scanResult.level);
    }

    private static int toPercentage(int power) {
        int i = 0;
        int MIN_DBM = -SCALE;
        if (power <= MIN_DBM) {
            i = 0;
        } else {
            i = SCALE + power;
        }

        return i;
    }
}
