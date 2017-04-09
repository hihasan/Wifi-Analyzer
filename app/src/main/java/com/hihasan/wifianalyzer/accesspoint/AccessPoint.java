package com.hihasan.wifianalyzer.accesspoint;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hihasan.wifianalyzer.R;
import com.hihasan.wifianalyzer.accesspoint.adapter.WifiRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AccessPoint extends AppCompatActivity {
    RecyclerView wifiRecycleView;
    ArrayList<String> wifiListModel = new ArrayList<>();
    WifiManager wifiManager;
    List<ScanResult> scanList;
    private StringBuilder sb = new StringBuilder();
    String TAG = "MainActivity";
    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_activity_main);
        wifiRecycleView = (RecyclerView) findViewById(R.id.wifiRecyclerView);
        wifiRecycleView.setLayoutManager(new LinearLayoutManager(this));
        scan = (Button) findViewById(R.id.scan);


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled() == false) {
                    Toast.makeText(getApplicationContext(), "wifi is disabled..", Toast.LENGTH_LONG).show();
                } else {
                    getWifiNetworksList();
                }
            }
        });

    }

    private void getWifiNetworksList() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        final WifiManager wifiManager =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        registerReceiver(new BroadcastReceiver() {

            @SuppressLint("UseValueOf")
            @Override
            public void onReceive(Context context, Intent intent) {
                //sb = new StringBuilder();
                scanList = wifiManager.getScanResults();
                //countWifi.setText(scanList.size());
                //sb.append("\n  Number Of Wifi connections :" + " " + scanList.size() + "\n\n");
                for (int i = 0; i < scanList.size(); i++) {
                    //Log.e(TAG, "sblist " + scanList.get(0));

//                    sb.append(new Integer(i + 1).toString() + ". ");
//                    sb.append((scanList.get(i)).toString());
//                    sb.append("\n\n");
                    wifiListModel.add(scanList.get(i).toString());
                }
                Log.e(TAG, "sb " + scanList.size());
                WifiRecyclerViewAdapter adapter = new WifiRecyclerViewAdapter(AccessPoint.this, wifiListModel);
                wifiRecycleView.setAdapter(adapter);
                wifiRecycleView.getAdapter().notifyDataSetChanged();

            }

        }, filter);
        wifiManager.startScan();

    }


}
