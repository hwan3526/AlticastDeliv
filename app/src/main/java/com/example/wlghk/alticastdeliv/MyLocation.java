package com.example.wlghk.alticastdeliv;

public class MyLocation {
    public static double latitude;
    public static double longitude;

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){ this.longitude = longitude; }
}
