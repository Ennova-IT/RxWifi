package it.ennova.rxwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

/**
 * This object is an extension of a {@link BroadcastReceiver} that mixes the classic Android API
 * for scanning the list of available networks and return them back as an {@link Observable}.
 */
class ResultReceiver extends BroadcastReceiver {

    /**
     * This variable is the one that will emit the networks whenever a {@link rx.Subscriber} is
     * subscribing to it
     */
    private ReplaySubject<ScanResult> subject;

    public ResultReceiver() {
        subject = ReplaySubject.create();
    }

    /**
     * This method implements a fluid API to start the scanning for new networks.
     */
    public ResultReceiver startScanningFrom(@NonNull Context context) {
        context.registerReceiver(this, RxWifi.filter);
        getWifiManager(context).startScan();
        return this;
    }

    private static WifiManager getWifiManager(@NonNull Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
            context.unregisterReceiver(this);
            Observable.from((getWifiManager(context)).getScanResults())
                    .subscribe(subject);
        }
    }

    /**
     * This method will return an {@link Observable} emitting the different {@link ScanResult} found
     * in the range of the device
     *
     * @return an instance of {@code Observable<ScanResult>}
     */
    public Observable<ScanResult> getObservable() {
        return subject.asObservable();
    }
}
