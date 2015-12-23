package it.ennova.rxwifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;

import it.ennova.rxwifi.internal.ResultReceiver;
import rx.Observable;


/**
 *
 */
public class RxWifi {

    public static Observable<ScanResult> from (@NonNull Context context) {
        return ResultReceiver.from(context).getObservable();
    }
}
