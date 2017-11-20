package com.coho.moki.data.remote.sub;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Khanh Nguyen on 11/17/2017.
 */

public class Brand implements Parcelable {

    public static final Parcelable.Creator<Brand> CREATOR = new Parcelable.Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };

    private Brand(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static Type getType() {
        return new TypeToken<List<Brand>>() {
        }.getType();
    }
}
