package com.example.wlghk.alticastdeliv;

public class MyLocation {
    private static double latitude;
    private static double longitude;

    public double getLatitude(){ return latitude; }
    public double getLongitude(){ return longitude; }
    public void setLatitude(double latitude){ this.latitude = latitude; }
    public void setLongitude(double longitude){ this.longitude = longitude; }
}
