package com.hihasan.wifianalyzer.accesspoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hihasan.wifianalyzer.R;

/**
 */

public class ShowDetailsActivity extends AppCompatActivity
{
    String TAG = "ShowDetailsActivity";
    String[] splitValue;
    String[] splitValue2;
    TextView distance, frequency, time, capabilities, bssid, rssi;
    String[] lArray, frequencyArray, timeAraay, capabilitiesArray, bssidArray, levelArray, frequencyValue;
    double exp;
    double f;
    int l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_activity_show_details);
        distance = (TextView) findViewById(R.id.distanceValueTxt);
        frequency = (TextView) findViewById(R.id.frequencyValueTxt);
        time = (TextView) findViewById(R.id.timestampValueTxt);
        capabilities = (TextView) findViewById(R.id.capabilitiesValueTxt);
        // bssid = (TextView) findViewById(R.id.BSSIDValueTxt);
        rssi = (TextView) findViewById(R.id.rssiValueTxt);

        String s = getIntent().getStringExtra("values");
        Log.e(TAG, "s " + s);
        splitValue = s.split(",");
        levelArray = splitValue[3].split(":");
        bssidArray = splitValue[1].split(":");
        capabilitiesArray = splitValue[2].split(":");
        frequencyArray = splitValue[4].split(":");
        timeAraay = splitValue[5].split(":");

        distance.setText("?");
        rssi.setText(levelArray[1] + " dBm");
        frequency.setText(frequencyArray[1]);
        capabilities.setText(capabilitiesArray[1]);
        time.setText(timeAraay[1]);
        frequencyValue = frequencyArray[1].split(" ");
        f = Double.parseDouble(frequencyValue[1]);
        lArray = levelArray[1].split("-");
        l= Integer.parseInt(lArray[1]);
        Log.e(TAG, "s value " + f);

        //bssid.setText(splitValue2[0].toString());

        double exp = (27.55 - (20 * Math.log10(f)) + Math.abs(l)) / 20.0;
        double distanceM = Math.pow(10.0, exp);

        distance.setText(""+distanceM+" m");
    }
}
