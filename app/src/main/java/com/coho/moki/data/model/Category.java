package com.coho.moki.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trung on 14/10/2017.
 */

public class Category implements Parcelable {

    private String categoryId;

    private String categoryName;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    protected Category(Parcel in) {
        categoryId = in.readString();
        categoryName = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryId);
        parcel.writeString(categoryName);
    }
}
