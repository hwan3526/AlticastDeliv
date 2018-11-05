package com.example.wlghk.alticastdeliv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable{
    ListView listView;
    ArrayAdapter<String> adapter;
    TextView textView;

    String htmlsource;
    String[] name;
    String[] lon;
    String[] lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        htmlsource = "";
        name = new String[30];
        lon = new String[30];
        lat = new String[30];

        Thread th = new Thread(MainActivity.this);
        th.start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getInformation();
                //textView.setText(htmlsource);//사용된 HTML 소스가 보고 싶다면 사용
                for(int i = 0;i < 30;i++)
                    adapter.add(name[i]+" "+lon[i]+" "+lat[i]);

                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void run(){
        getHTML("피자");
    }

    void getHTML(String keyword) {
        String str;

        try {
            URL url = new URL("https://m.map.naver.com/search2/search.nhn?query="+keyword+"&sm=clk&centerCoord=37.291859079115994:126.97775963708631#/list");
            //또는 "https://m.map.naver.com/search2/search.nhn?query=" + /*현재 위치 주소*/ keyword + "&siteLocation=&queryRank=&type="
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("var searchResult"));
            htmlsource = str;

            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getInformation(){
        int index=0, start, end;

        for(int i = 0; i < 30; i++){
            //점포명
            index = htmlsource.indexOf("\"index\"", index);
            start = (htmlsource.indexOf("\"name\"", index))+9;
            end = htmlsource.indexOf("\"", start);
            name[i] = htmlsource.substring(start, end);
            //경도
            start = (htmlsource.indexOf("\"lng\"", end))+8;
            end = htmlsource.indexOf("\"", start);
            lon[i] = htmlsource.substring(start, end);
            //위도
            start = (htmlsource.indexOf("\"lat\"", end))+8;
            end = htmlsource.indexOf("\"", start);
            lat[i] = htmlsource.substring(start, end);
            index = end;
        }
    }
}
