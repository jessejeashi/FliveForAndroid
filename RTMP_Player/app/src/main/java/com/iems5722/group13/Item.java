package com.iems5722.group13;

import java.util.ArrayList;
/**
 * Created by Administrator on 2016/2/3.
 */
public class Item {
    public String cname;
    public String topic;
    public String uname;

    public Item(String cname, String topic, String uname) {
        this.cname = cname;
        this.topic = topic;
        this.uname = uname;
    }

    //for testing
    public static ArrayList<Item> testItems() {
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("Harry", "San Diego","Alen"));
        items.add(new Item("Marla", "San Francisco","Alen"));
        items.add(new Item("Sarah", "San Marco","Alen"));
        return items;
    }
}

