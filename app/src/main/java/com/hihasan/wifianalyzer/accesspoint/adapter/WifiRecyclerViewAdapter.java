package com.hihasan.wifianalyzer.accesspoint.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hihasan.wifianalyzer.R;
import com.hihasan.wifianalyzer.accesspoint.ShowDetailsActivity;

import java.util.ArrayList;

/**
 */

public class WifiRecyclerViewAdapter extends RecyclerView.Adapter<WifiRecyclerViewAdapter.ViewHolder> {
    ArrayList<String> wifiListModel;
    TextView wifiNameTxt;
    Activity activity;
    String TAG = "WifiRecyclerViewAdapter";
    CardView cardView;
    String[] splitValue;

    public WifiRecyclerViewAdapter(Activity activity, ArrayList<String> wifiListModel) {

        this.activity = activity;
        this.wifiListModel = wifiListModel;

    }

    @Override
    public WifiRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View wifiList = layoutInflater.inflate(R.layout.access_wifi_name, parent, false);
        ViewHolder viewHolder = new ViewHolder(wifiList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WifiRecyclerViewAdapter.ViewHolder holder, final int position) {
        Log.e(TAG, "list" + wifiListModel.get(position));
        String ssid = wifiListModel.get(position).toString();
        Log.e(TAG, "ssid " + ssid);
        splitValue = ssid.split(",");
        wifiNameTxt.setText(splitValue[0]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShowDetailsActivity.class);
                intent.putExtra("values", wifiListModel.get(position).toString());
                activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return wifiListModel.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
            wifiNameTxt = (TextView) itemView.findViewById(R.id.wifiName);
            cardView = (CardView) itemView.findViewById(R.id.wifiCard);
        }
    }

}
