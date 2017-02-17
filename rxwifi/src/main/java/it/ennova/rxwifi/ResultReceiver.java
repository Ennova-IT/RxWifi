package it.ennova.rxwifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import rx.Observable;
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
    private final Context context;

    public ResultReceiver(final Context context) {
        subject = ReplaySubject.create();
        this.context = context.getApplicationContext();
    }

    /**
     * This method implements a fluid API to start the scanning for new networks.
     */
    public ResultReceiver startScanningFrom() {
        context.registerReceiver(this, RxWifi.filter);
        getWifiManager(context).startScan();
        return this;
    }

    @SuppressLint("WifiManagerPotentialLeak")
    private static WifiManager getWifiManager(@NonNull Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
            this.context.unregisterReceiver(this);
            Observable.from((getWifiManager(this.context)).getScanResults())
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
