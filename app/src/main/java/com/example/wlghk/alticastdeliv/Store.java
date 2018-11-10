package com.example.wlghk.alticastdeliv;

import java.util.ArrayList;

public class Store {
    private String id, name;
    private int distance;
    private ArrayList<Menu> menulist;

    public Store(String id, String name, int distance){
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.menulist = new ArrayList<Menu>();
    }

    public String getID(){ return this.id; }
    public String getName(){ return this.name; }
    public int getDistance(){ return this.distance; }
    public ArrayList<Menu> getMenulist(){ return this.menulist; }
    public Menu getMenu(int i){ return this.menulist.get(i); }
    public void setID(String id){ this.id = id;}
    public void setName(String name){ this.name = name; }
    public void setDistance(int distance){ this.distance = distance; }
    public void setMenulist(ArrayList<Menu> menulist){ this.menulist = menulist; }
    public void setMenu(Menu menu){ this.menulist.add(menu); }
    public void setMenu(String name, int cost, String photo){ this.menulist.add(new Menu(name, cost, photo)); }
}

class Menu{
    private String name;
    private int cost;
    private String photo;

    public Menu(String name, int cost, String photo){
        this.name = name;
        this.cost = cost;
        this.photo = photo;
    }

    public String getName(){ return this.name; }
    public int getCost(){ return this.cost; }
    public String getPhoto(){ return this.photo; }
    public void setName(String name){ this.name = name; }
    public void setCost(int cost){ this.cost = cost; }
    public void setPhoto(String photo){ this.photo = photo; }
}