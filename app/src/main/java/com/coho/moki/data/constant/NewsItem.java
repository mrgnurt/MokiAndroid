package com.coho.moki.data.constant;

/**
 * Created by trung on 20/11/2017.
 */

public class NewsItem {

    private String time;
    private String name;

    public NewsItem(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
