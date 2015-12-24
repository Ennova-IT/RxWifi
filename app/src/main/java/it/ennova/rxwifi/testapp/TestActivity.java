package it.ennova.rxwifi.testapp;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import it.ennova.rxwifi.RxWifi;
import it.ennova.rxwifi.internals.ScanResultUtils;
import rx.functions.Action1;

public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RxWifi.from(this).subscribe(new Action1<ScanResult>() {
            @Override
            public void call(ScanResult result) {
                Log.d("RX-WIFI-Reactive", ScanResultUtils.toWiFiNetwork(result).toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("RX-WIFI-Reactive", throwable.getMessage());
            }
        });
    }

}
