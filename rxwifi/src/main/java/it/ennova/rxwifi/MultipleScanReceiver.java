package it.ennova.rxwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Emitter;
import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;

class MultipleScanReceiver {

    private BroadcastReceiver receiver;

    Observable<List<ScanResult>> scan(final Context context, final int times) {
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        return Observable.fromEmitter(new Action1<Emitter<List<ScanResult>>>() {

            @Override
            public void call(final Emitter<List<ScanResult>> emitter) {
                if (receiver == null) {
                    receiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            emitter.onNext(wifiManager.getScanResults());
                            wifiManager.startScan();
                        }
                    };
                    context.registerReceiver(receiver, RxWifi.filter);
                }

                wifiManager.startScan();
            }
        }, Emitter.BackpressureMode.LATEST)
                .doOnUnsubscribe(getUnsubscribeAction(context))
                .take(times)
                .subscribeOn(Schedulers.io());
    }

    @NonNull
    private Action0 getUnsubscribeAction(final Context context) {
        return new Action0() {
            @Override
            public void call() {
                context.unregisterReceiver(receiver);
            }
        };
    }
}
