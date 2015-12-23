package it.ennova.rxwifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;

import rx.Observable;


/**
 * This class is the main entry point for the library, and contains the methods for retrieving the
 * {@link Observable} emitting the {@link ScanResult} containing the different networks available
 * in the device's range
 */
public class RxWifi {

    private final static ResultReceiver receiver = new ResultReceiver();

    /**
     * This method is the one that will obtain an {@link Observable} with all the data related to
     * the networks in range of the device
     */
    public static Observable<ScanResult> from (@NonNull Context context) {
        return receiver.startScanningFrom(context).getObservable();
    }
}
