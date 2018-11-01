package com.example.wlghk.alticastdeliv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

class SearchResult{
    public String[] name;
    public String[] addr;

    public SearchResult(){
        this.name = new String[10];
        this.addr = new String[10];
    }
}

public class MapManager {
    private String source;
    private String name;
    private String addr;
    private SearchResult sr;

    public MapManager(){
        this.source = "";
        this.name = "";
        this.addr = "";
        this.sr = new SearchResult();
    }

    public void getHTML(String keyword) {
        String htmlCode = "";

        try {
            URL url = new URL("https://www.google.com/");
            //URL url = new URL("https://map.naver.com/?query=" + keyword);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                htmlCode += inputLine;

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.source = htmlCode;
    }

    public SearchResult getInformation(){
        int start, end;

        start = source.indexOf("장소 리스트");
        start = source.indexOf("<dt>", start);
        end = source.indexOf( "a href", start);
        for(int i = 0;i < 10;i++) {
            this.name = "";
            this.addr = "";
            //점포명 추출
            while(true) {
                start = source.indexOf(">", end) + 1;
                end = source.indexOf("<", start);
                if("" == source.substring(start, end))
                    continue;
                else
                    name += source.substring(start, end);

                if ("</a>" == source.substring(end, source.indexOf(">", end) + 1))
                    break;
            }

            //주소 추출
            end = source.indexOf( "addr", start);
            start = source.indexOf(">", end) + 1;
            end = source.indexOf("          ", start);//띄어쓰기 10칸?
            addr = source.substring(start, end);

            start = source.indexOf("<dt>", end);
            end = source.indexOf("a href", start);

            this.sr.name[i] = this.name;
            this.sr.addr[i] = this.addr;

        }
        /*
        this.sr.name[0] = this.source;
        this.sr.addr[0] = "서울";
        this.sr.name[1] = "2";
        this.sr.addr[1] = "대전";
        this.sr.name[2] = "3";
        this.sr.addr[2] = "대구";
        this.sr.name[3] = "4";
        this.sr.addr[3] = "부산";
        this.sr.name[4] = "5";
        this.sr.addr[4] = "광주";
        this.sr.name[5] = "6";
        this.sr.addr[5] = "인천";
        this.sr.name[6] = "7";
        this.sr.addr[6] = "용인";
        this.sr.name[7] = "8";
        this.sr.addr[7] = "부천";
        this.sr.name[8] = "9";
        this.sr.addr[8] = "수원";
        this.sr.name[9] = "10";
        this.sr.addr[9] = "구리";
        */
        return sr;
    }
}
