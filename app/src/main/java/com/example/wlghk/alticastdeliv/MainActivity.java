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
    TextView textView;

    String searchhtmlsource, menuhtmlsource;
    ArrayList<Store> searchresult;
    LocationManager lm;
    MyLocation mylocation;
    int numofresult = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        searchhtmlsource = "";
        menuhtmlsource = "";
        searchresult = new ArrayList<Store>();
        mylocation = new MyLocation();

        Thread th = new Thread(MainActivity.this);
        th.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(searchhtmlsource);//사용된 HTML 소스가 보고 싶다면 사용
                for(int i = 0;i < numofresult;i++)
                    adapter.add(searchresult.get(i).getName()+" "+searchresult.get(i).getDistance()+" 메뉴갯수: "+searchresult.get(i).getMenulist().size());

                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void run(){
        getLocation();
        getInformation("피자");
    }

    void getInformation(String keyword){
        int index=0, start, end;
        String name, id;
        double distance;
        ArrayList<Menu> menulist;

        getSearchHTML(keyword);
        for(int i = 0; i < numofresult; i++){
            index = searchhtmlsource.indexOf("\"index\"", index);
            //점포ID
            start = (searchhtmlsource.indexOf("\"id\"", index))+8;
            end = searchhtmlsource.indexOf("\"", start);
            id = searchhtmlsource.substring(start, end);
            //점포명
            start = (searchhtmlsource.indexOf("\"name\"", index))+9;
            end = searchhtmlsource.indexOf("\"", start);
            name = searchhtmlsource.substring(start, end);
            //거리
            start = (searchhtmlsource.indexOf("\"distance\"", end))+13;
            end = searchhtmlsource.indexOf("\"", start);
            distance =  Float.valueOf(searchhtmlsource.substring(start, end));
            //메뉴목록
            getMenuHTML(id);
            menulist = getMenuInformation(id);

            searchresult.add(new Store(name, (int)distance, menulist));
        }
    }

    ArrayList<Menu> getMenuInformation(String id){
        int index=0, start, end;
        String name, photo, tmp;
        int cost;

        ArrayList<Menu> menulist = new ArrayList<Menu>();
        for(int i = 0; i < numofresult; i++){
            index = searchhtmlsource.indexOf("\"display:block\"", index);
            //가격
            start = (searchhtmlsource.indexOf("\"price\"", index))+8;
            end = searchhtmlsource.indexOf("원", start);
            tmp = searchhtmlsource.substring(start, end);
            for(int j = 0; j < tmp.length();j++){
                if(tmp.charAt(j) == ',')
                    tmp = tmp.substring(0,j)+tmp.substring(j+1,tmp.length()-1);
            }
            cost = Integer.valueOf(tmp);
            //사진
            if(searchhtmlsource.indexOf("\"photo_count\"",end)!=-1){
                photo = "https://m.store.naver.com/restaurants/" + id + "#photoId=menuSample_"+ String.valueOf(i);
                end = searchhtmlsource.indexOf("\"photo_count\"",end);
            }
            else{
                photo = "";
            }
            //이름
            start = (searchhtmlsource.indexOf("<span>", end))+6;
            end = searchhtmlsource.indexOf("</span>", start);
            name = searchhtmlsource.substring(start, end);

            menulist.add(new Menu(name,cost,photo));
        }
        return menulist;
    }

    void getSearchHTML(String keyword) {
        String str;

        try {
            URL url = new URL("https://m.map.naver.com/search2/search.nhn?query="+keyword+"&sm=clk&centerCoord="+mylocation.getLatitude()+":"+mylocation.getLongitude());
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("var searchResult"));
            searchhtmlsource = str;

            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getMenuHTML(String storeid) {
        String str;

        try {
            URL url = new URL("https://m.store.naver.com/restaurants/" + storeid);
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("list_menu"));
            menuhtmlsource = str;

            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Log.d("test", "onLocationChanged, location:"+ location);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            mylocation.setLongitude(longitude);
            mylocation.setLatitude(latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }
    };
}

class Store {
    String name;
    int distance;
    ArrayList<Menu> menulist;

    public Store(){

    }
    public Store(String name, int distance, ArrayList<Menu> menulist){
        this.name = name;
        this.distance = distance;
        this.menulist = menulist;
    }

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
