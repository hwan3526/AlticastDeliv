package com.example.wlghk.alticastdeliv;

import java.io.BufferedReader;
import java.io.InputStream;
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

    public void getHTML(String keyword){
        String URLdomain = "https://map.naver.com/?query=" + keyword;
        StringBuffer url_content = new StringBuffer();
        try
        {
            URL url = new URL(URLdomain);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String inStr;
            while((inStr = br.readLine()) != null) {
                url_content.append(inStr+"\n");
            }

            this.source = new String(url_content);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

        return sr;
    }
}
