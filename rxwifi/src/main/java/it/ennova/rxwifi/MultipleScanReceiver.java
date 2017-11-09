package it.ennova.rxwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

class MultipleScanReceiver {

    private BroadcastReceiver receiver;

    Flowable<List<ScanResult>> scan(final Context context, final int times) {
        final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager == null){
            return Flowable.empty();
        }
        return Flowable.create(new FlowableOnSubscribe<List<ScanResult>>() {
            @Override
            public void subscribe(final FlowableEmitter<List<ScanResult>> emitter) throws Exception {
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
        }, BackpressureStrategy.LATEST)
            .doOnComplete(getUnsubscribeAction(context))
            .take(times)
            .subscribeOn(Schedulers.io());

    }

    @NonNull
    private Action getUnsubscribeAction(final Context context) {
        return new Action() {
            @Override
            public void run() throws Exception {
                context.unregisterReceiver(receiver);
            }
        };
    }
}
