package com.example.wlghk.alticastdeliv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Crawler extends Thread{
    private String keyword;
    private ArrayList<Store> searchresult;
    private MyLocation mylocation;

    public Crawler(String keyword){
        this.keyword = keyword;
        this.searchresult = new ArrayList<Store>();
    }

    public ArrayList<Store> getSearchresult(){
        return this.searchresult;
    }

    public void getStoreInformation(String keyword){
        int index=0, start, end;
        String name, id;
        int distance;

        String htmlsource = getSearchHTML(keyword);
        index = htmlsource.indexOf("\"index\"", index);
        while(index != -1){
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
            distance = Double.valueOf(htmlsource.substring(start, end)).intValue();

            searchresult.add(new Store(id, name, distance));
            index = htmlsource.indexOf("\"index\"", end);
        }
    }

    public void getMenuInformation(int idx){
        int index=0, start, end;
        String name, photo;
        int cost;

        String htmlsource = getMenuHTML(this.searchresult.get(idx).getID());
        index = htmlsource.indexOf("\"display:block\"", index);
        for(int i = 0; index != -1; i++){
            //가격
            start = (htmlsource.indexOf("\"price\"", index))+8;
            end = htmlsource.indexOf("원", start);
            cost = Integer.valueOf((htmlsource.substring(start, end)).replaceAll(",",""));
            //사진
            if(htmlsource.indexOf("\"photo_count\"",end)!=-1){
                photo = "https://m.store.naver.com/restaurants/" + this.searchresult.get(idx).getID() + "#photoId=menuSample_"+ String.valueOf(i);
                end = htmlsource.indexOf("\"photo_count\"",end);
            }
            else{
                photo = "";
            }
            //이름
            start = (htmlsource.indexOf("<span>", end))+6;
            end = htmlsource.indexOf("</span>", start);
            name = htmlsource.substring(start, end);

            this.searchresult.get(idx).getMenulist().add(new Menu(name,cost,photo));
            index = htmlsource.indexOf("\"display:block\"", end);
        }
    }

    public String getSearchHTML(String keyword) {
        String str = "";

        try {
            URL url = new URL("https://m.map.naver.com/search2/search.nhn?query="+keyword+"&sm=clk&centerCoord=37.291636:126.977840");
            //URL url = new URL("https://m.map.naver.com/search2/search.nhn?query="+keyword+"&sm=clk&centerCoord="+this.mylocation.getLatitude()+":"+this.mylocation.getLongitude());
            //URL url = new URL("https://m.map.naver.com/search2/search.nhn?query=경기도 수원시 장안구 천천동 "+keyword+"&sm=hty");
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("var searchResult"));
            str = str.substring(str.indexOf("var searchResult"));

            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public String getMenuHTML(String storeid) {
        String str = "";

        try {
            URL url = new URL("https://m.store.naver.com/restaurants/" + storeid);
            InputStreamReader isr = new InputStreamReader(url.openStream(),"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            while(!(str = br.readLine()).contains("\"list_menu\""));
            str = str.substring(str.indexOf("\"list_menu\""));

            br.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void run(){
        this.getStoreInformation(this.keyword);
        for(int i = 0 ; i < this.searchresult.size() ; i++) {
            this.getMenuInformation(i);
        }
    }
}
