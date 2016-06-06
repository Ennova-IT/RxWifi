package it.ennova.rxwifi.internals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.util.List;

import rx.Subscriber;


public class WiFiBroadcastManager extends BroadcastReceiver {
    private Subscriber<? super List<ScanResult>> subscriber = null;
    private final WifiManager wifiManager;

    private static final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

    public WiFiBroadcastManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    public void setSubscriber(@NonNull Subscriber<? super List<ScanResult>> subscriber) {
        if (this.subscriber == null) {
            this.subscriber = subscriber;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        subscriber.onNext(wifiManager.getScanResults());
    }

    public static IntentFilter getFilter() {
        return filter;
    }
}

