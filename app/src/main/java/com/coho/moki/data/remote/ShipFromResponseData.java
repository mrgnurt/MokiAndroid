package com.coho.moki.data.remote;

import android.support.annotation.NonNull;

/**
 * Created by trung on 18/11/2017.
 */

public class ShipFromResponseData implements Comparable<ShipFromResponseData> {

    private int order;
    private String name;

    public ShipFromResponseData(int order, String name) {
        this.order = order;
        this.name = name;
    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull ShipFromResponseData o) {
        return this.getName().compareTo(o.getName());
    }
}
