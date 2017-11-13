package com.coho.moki.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trung on 07/11/2017.
 */

public class Size implements Parcelable {

    private String sizeId;
    private String sizeName;

    public Size(String sizeId, String sizeName) {
        this.sizeId = sizeId;
        this.sizeName = sizeName;
    }

    protected Size(Parcel in) {
        sizeId = in.readString();
        sizeName = in.readString();
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sizeId);
        parcel.writeString(sizeName);
    }
}
