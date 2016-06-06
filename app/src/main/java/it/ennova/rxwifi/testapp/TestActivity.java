package it.ennova.rxwifi.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import it.ennova.rxwifi.RxWifi;
import it.ennova.rxwifi.internals.ScanResultUtils;
import rx.functions.Action1;

public class TestActivity extends AppCompatActivity{
    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startScanning();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startScanning();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void startScanning() {
        RxWifi.from(getApplicationContext()).subscribe(new Action1<ScanResult>() {
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
