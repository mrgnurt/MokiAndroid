package com.coho.moki.data.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

/**
 * Created by trung on 31/10/2017.
 */

public class BrandResponceData implements Comparable<BrandResponceData> {
    private String id;
    private String brandName;


    public BrandResponceData(String id, String name) {
        this.id = id;
        this.brandName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return brandName;
    }

    public void setName(String name) {
        this.brandName = name;
    }

    @Override
    public int compareTo(@NonNull BrandResponceData o) {
        return this.getName().compareTo(o.getName());
    }

    public static Type getType() {
        return new TypeToken<List<BrandResponceData>>() {
        }.getType();
    }
}
