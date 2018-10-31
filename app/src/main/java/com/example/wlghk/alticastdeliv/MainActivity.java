package com.example.wlghk.alticastdeliv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    EditText editText,editText2;
    ListView listView, listView2;
    ArrayAdapter<String> adapter, adapter2;

    MapManager mapmanager;
    SearchResult sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        listView = (ListView) findViewById(R.id.listView);
        listView2 = (ListView) findViewById(R.id.listView2);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);

        mapmanager = new MapManager();
        mapmanager.getHTML("피자");
        sr = mapmanager.getInformation();
        for(int i = 0;i < 10;i++){
            adapter.add(sr.name[i]);
            adapter2.add(sr.addr[i]);
        }
    }
}
