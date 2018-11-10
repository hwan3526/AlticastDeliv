package com.example.wlghk.alticastdeliv;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ListView listView;
    ArrayAdapter<String> adapter;

    LocationManager locationmanager;
    MyLocation mylocation;
    Crawler crawler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        mylocation = new MyLocation();
        crawler = new Crawler("치킨");
        //getLocation();

        crawler.start();

        try{
            crawler.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        for(int i = 0;i < crawler.getSearchresult().size();i++) {
            ArrayList<Menu> menulist = crawler.getSearchresult().get(i).getMenulist();
            for (int j = 0; j < crawler.getSearchresult().get(i).getMenulist().size(); j++)
                adapter.add(crawler.getSearchresult().get(i).getName()+", 거리: "+crawler.getSearchresult().get(i).getDistance()+"m, "+menulist.get(j).getName()+", "+menulist.get(j).getCost()+"원, "+menulist.get(j).getPhoto());
        }
        listView.setAdapter(adapter);
    }

    void getLocation(){
        locationmanager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, LocationListener);
            locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, LocationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private final LocationListener LocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("GPS", "onLocationChanged, location:"+ location);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            mylocation.setLongitude(longitude);
            mylocation.setLatitude(latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("GPS", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("GPS", "onProviderEnabled, provider:" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("GPS", "onProviderDisabled, provider:" + provider);
        }
    };
}
