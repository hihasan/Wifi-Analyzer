package com.hihasan.wifianalyzer;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hihasan.wifianalyzer.contourgraph.ContourMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private ListView listViewWifi;

    private HashMap<String, List<ScanResult>> mapScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //listview wifi
        listViewWifi = (ListView) this.findViewById(R.id.listView_wifi);
        mapScan = new HashMap<>();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //wifimanager
        WifiManager wifiMan = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        ArrayList<ScanResult> scanResults = (ArrayList<ScanResult>) wifiMan.getScanResults();
        for (ScanResult scanResult : scanResults)
        {

            ArrayList<ScanResult> listScanResult;

            if ((listScanResult = (ArrayList<ScanResult>) mapScan.get(scanResult.SSID)) == null) {
                listScanResult = new ArrayList<>();
                listScanResult.add(scanResult);
                mapScan.put(scanResult.SSID, listScanResult);
            } else {
                listScanResult.add(scanResult);
                mapScan.put(scanResult.SSID, listScanResult);
            }
        }

        ScanResultAdapter adapter = new ScanResultAdapter(this, R.layout.row, scanResults);

        listViewWifi.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listViewWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Object o = listViewWifi.getItemAtPosition(position);
                ScanResult scanResult = (ScanResult) o;

                Toast.makeText(getBaseContext(), "Nombre : "+mapScan.get(scanResult.SSID).size(), Toast.LENGTH_SHORT).show();
            }
        });


//        WifiInfo wifiInf = wifiMan.getConnectionInfo();
//        String macAddr = wifiInf.getMacAddress();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_contour)
        {
            Intent n=new Intent(MainActivity.this, ContourMain.class);
            startActivity(n);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
