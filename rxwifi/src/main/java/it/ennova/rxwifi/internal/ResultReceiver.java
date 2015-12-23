package it.ennova.rxwifi.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.subjects.ReplaySubject;

/**
 *
 */
public class ResultReceiver extends BroadcastReceiver {

    private ReplaySubject<ScanResult> subject;

    public static ResultReceiver from(@NonNull Context context) {
        return new ResultReceiver(context);
    }

    private ResultReceiver(@NonNull Context context) {
        subject = ReplaySubject.create();
        registerReceiver(context);
        ((WifiManager)context.getSystemService(Context.WIFI_SERVICE)).startScan();
    }

    private void registerReceiver(@NonNull Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())){
            context.unregisterReceiver(this);
            Observable.from(((WifiManager)context.getSystemService(Context.WIFI_SERVICE)).getScanResults())
                    .subscribe(subject);
        }
    }

    public Observable<ScanResult> getObservable() {
        return subject.asObservable();
    }
}
