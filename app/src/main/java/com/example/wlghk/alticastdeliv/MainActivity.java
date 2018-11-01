package com.example.wlghk.alticastdeliv;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Runnable{
    ListView listView, listView2;
    ArrayAdapter<String> adapter, adapter2;
    TextView textView;

    String htmlsource;
    String[] name;
    String[] addr;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            getInformation();
            textView.setText(htmlsource);
            for(int i = 0;i < 10;i++){
                adapter.add(name[i]);
                adapter2.add(addr[i]);
            }

            listView.setAdapter(adapter);
            listView2.setAdapter(adapter2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView2 = (ListView) findViewById(R.id.listView2);
        textView = (TextView) findViewById(R.id.textView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        name = new String[10];
        addr = new String[10];

        Thread th = new Thread(MainActivity.this);
        th.start();
    }

    @Override
    public void run(){
        htmlsource = getHTML("피자");

        handler.sendEmptyMessage(0);
    }

    String getHTML(String keyword) {
        String htmlCode = "";

        try {
            //URL url = new URL("https://www.google.com/");
            URL url = new URL("https://map.naver.com/?query=" + /*현재 위치 주소*/ keyword);
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(isr);

            String inputLine;
            while ((inputLine = br.readLine()) != null)
                htmlCode += inputLine;

            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return htmlCode;
    }

    void getInformation(){
        int start, end;
        /*
        start = htmlsource.indexOf("장소 리스트");
        start = htmlsource.indexOf("<dt>", start);
        end = htmlsource.indexOf( "a href", start);
        for(int i = 0;i < 10;i++) {
            name[i] = "";
            addr[i] = "";
            //점포명 추출
            while(true) {
                start = htmlsource.indexOf(">", end) + 1;
                end = htmlsource.indexOf("<", start);
                if("" == htmlsource.substring(start, end))
                    continue;
                else
                    name[i] += htmlsource.substring(start, end);

                if ("</a>" == htmlsource.substring(end, htmlsource.indexOf(">", end) + 1))
                    break;
            }

            //주소 추출
            end = htmlsource.indexOf( "addr", start);
            start = htmlsource.indexOf(">", end) + 1;
            end = htmlsource.indexOf("          ", start);//띄어쓰기 10칸?
            addr[i] = htmlsource.substring(start, end);

            start = htmlsource.indexOf("<dt>", end);
            end = htmlsource.indexOf("a href", start);
        }
        */
        fail();
    }

    void fail(){
        name[0] = "1";
        addr[0] = "서울";
        name[1] = "2";
        addr[1] = "대전";
        name[2] = "3";
        addr[2] = "대구";
        name[3] = "4";
        addr[3] = "부산";
        name[4] = "5";
        addr[4] = "광주";
        name[5] = "6";
        addr[5] = "인천";
        name[6] = "7";
        addr[6] = "용인";
        name[7] = "8";
        addr[7] = "부천";
        name[8] = "9";
        addr[8] = "수원";
        name[9] = "10";
        addr[9] = "구리";
    }
}
