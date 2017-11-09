package it.ennova.rxwifi.testapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.functions.Consumer;
import it.ennova.rxwifi.RxWifi;
import it.ennova.rxwifi.internals.ScanResultUtils;

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
        RxWifi.from(getApplicationContext()).subscribe(new Consumer<ScanResult>() {
            @Override
            public void accept(ScanResult result) throws Exception {
                Log.d("RX-WIFI-SingleScan", ScanResultUtils.toWiFiNetwork(result).toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("RX-WIFI-SingleScan", throwable.getMessage());
            }
        });

        /*RxWifi.from(getApplicationContext(), 5).map(new Func1<List<ScanResult>, Integer>() {
            @Override
            public Integer call(List<ScanResult> results) {
                return results.size();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("RX-WIFI-MultipleScans", "Found " + integer + " networks");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("RX-WIFI-MultipleScans", throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d("RX-WIFI-MultipleScans", "COMPLETED!");
            }
        });*/
    }

}
