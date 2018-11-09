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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Runnable{
    ListView listView;
    ArrayAdapter<String> adapter;

    ArrayList<Store> searchresult;
    LocationManager lm;
    MyLocation mylocation;
    int numofresult = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        searchresult = new ArrayList<Store>();
        mylocation = new MyLocation();
        getLocation();
        Log.d("myLocation", mylocation.getLatitude()+": "+mylocation.getLongitude());

        Thread th = new Thread(MainActivity.this);
        th.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i < numofresult;i++)
                    adapter.add(searchresult.get(i).getName()+", 거리: "+searchresult.get(i).getDistance()+"m, 메뉴갯수: "+((searchresult.get(i)).getMenulist()).size());

                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void run(){
        getStoreInformation("치킨");
        for(int i = 0 ; i < searchresult.size() ; i++)
            getMenuInformation(i);
    }

    void getStoreInformation(String keyword){
        int index=0, start, end;
        String name, id;
        int distance;

        String htmlsource = getSearchHTML(keyword);
        for(int i = 0; i < numofresult; i++){
            index = htmlsource.indexOf("\"index\"", index);
            //점포ID
            start = (htmlsource.indexOf("\"id\"", index))+8;
            end = htmlsource.indexOf("\"", start);
            id = htmlsource.substring(start, end);
            //점포명
            start = (htmlsource.indexOf("\"name\"", index))+9;
            end = htmlsource.indexOf("\"", start);
            name = htmlsource.substring(start, end);
            //거리
            start = (htmlsource.indexOf("\"distance\"", end))+13;
            end = htmlsource.indexOf("\"", start);
            distance =  Integer.valueOf(htmlsource.substring(start, end));
            Log.d("StoreInfo",id+" "+name+" "+distance);

            searchresult.add(new Store(id, name, distance));
        }
    }

    void getMenuInformation(int idx){
        int index=0, start, end;
        String name, photo;
        int cost;

        String htmlsource = getMenuHTML(searchresult.get(idx).id);
        for(int i = 0; i < numofresult; i++){
            index = htmlsource.indexOf("\"display:block\"", index);
            //가격
            start = (htmlsource.indexOf("\"price\"", index))+8;
            end = htmlsource.indexOf("원", start);
            cost = Integer.valueOf((htmlsource.substring(start, end)).replaceAll(",",""));
            //사진
            if(htmlsource.indexOf("\"photo_count\"",end)!=-1){
                photo = "https://m.store.naver.com/restaurants/" + searchresult.get(idx).id + "#photoId=menuSample_"+ String.valueOf(i);
                end = htmlsource.indexOf("\"photo_count\"",end);
            }
            else{
                photo = "";
            }
            //이름
            start = (htmlsource.indexOf("<span>", end))+6;
            end = htmlsource.indexOf("</span>", start);
            name = htmlsource.substring(start, end);

            Log.d("MenuList",name+" "+cost+" "+photo);
            ((searchresult.get(idx)).getMenulist()).add(new Menu(name,cost,photo));
        }
    }

    String getSearchHTML(String keyword) {
        String str = "";

        try {
            URL url = new URL("https://m.map.naver.com/search2/search.nhn?query="+keyword+"&sm=clk&centerCoord="+mylocation.getLatitude()+":"+mylocation.getLongitude());
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("var searchResult"));
            str = str.substring(str.indexOf("var searchResult"));
            Log.d("searchhtmlsource", str);

            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    String getMenuHTML(String storeid) {
        String str = "";

        try {
            URL url = new URL("https://m.store.naver.com/restaurants/" + storeid);
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("\"list_menu\""));
            str = str.substring(str.indexOf("\"list_menu\""));
            Log.d("menuhtmlsource", str);

            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    void getLocation(){
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, LocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, LocationListener);
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

class Store {
    String id, name;
    int distance;
    ArrayList<Menu> menulist;

    public Store(){

    }
    public Store(String id, String name, int distance){
        this.name = name;
        this.distance = distance;
        this.menulist = new ArrayList<Menu>();
    }

    public String getID(){ return this.id; }
    public String getName(){
        return this.name;
    }
    public int getDistance(){
        return this.distance;
    }
    public ArrayList<Menu> getMenulist(){
        return this.menulist;
    }
    public Menu getMenu(int i){
        return this.menulist.get(i);
    }
    public void setID(String id){ this.id = id;}
    public void setName(String name){
        this.name = name;
    }
    public void setDistance(int distance){
        this.distance = distance;
    }
    public void setMenulist(ArrayList<Menu> menulist){
        this.menulist = menulist;
    }
    public void setMenu(Menu menu){
        this.menulist.add(menu);
    }
    public void setMenu(String name, int cost, String photo){
        this.menulist.add(new Menu(name, cost, photo));
    }
}

class Menu{
    String name;
    int cost;
    String photo;

    public Menu(String name, int cost, String photo){
        this.name = name;
        this.cost = cost;
        this.photo = photo;
    }

    public String getName(){
        return this.name;
    }
    public int getCost(){
        return this.cost;
    }
    public String getPhoto(){
        return this.photo;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }
}
