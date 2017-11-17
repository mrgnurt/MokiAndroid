package com.coho.moki.data.remote;

import android.os.Parcel;
import android.os.Parcelable;

import com.coho.moki.data.remote.sub.Brand;
import com.coho.moki.data.remote.sub.Size;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/15/2017.
 */

public class ProductCategoryResponse implements Parcelable {

    private String id;
    private String name;
    private int hasBrand;
    private int hasName;
    private String parentId;
    private int hasChild;
    private int hasSize;
    private String createdAt;
    private List<Size> sizes;
    private List<Brand> brands;

    public static final Parcelable.Creator<ProductCategoryResponse> CREATOR = new Parcelable.Creator<ProductCategoryResponse>() {
        @Override
        public ProductCategoryResponse createFromParcel(Parcel source) {
            return new ProductCategoryResponse(source);
        }

        @Override
        public ProductCategoryResponse[] newArray(int size) {
            return new ProductCategoryResponse[size];
        }
    };

    private ProductCategoryResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        hasBrand = in.readInt();
        hasName = in.readInt();
        parentId = in.readString();
        hasChild = in.readInt();
        hasSize = in.readInt();
        createdAt = in.readString();
        sizes = in.readArrayList(Size.class.getClassLoader());
        brands = in.readArrayList(Brand.class.getClassLoader());
    }

    public ProductCategoryResponse() {}

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getHasBrand() {
        return hasBrand;
    }

    public void setHasBrand(int hasBrand) {
        this.hasBrand = hasBrand;
    }

    public int getHasName() {
        return hasName;
    }

    public void setHasName(int hasName) {
        this.hasName = hasName;
    }

    public int getHasChild() {
        return hasChild;
    }

    public void setHasChild(int hasChild) {
        this.hasChild = hasChild;
    }

    public int getHasSize() {
        return hasSize;
    }

    public void setHasSize(int hasSize) {
        this.hasSize = hasSize;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    @Override
    public String toString() {
        return "{" +
                "name: " + name
                + ",\n hasBrand: " + hasBrand
                + ",\n hasName: " + hasName
                + ",\n parentId: " + parentId
                + ",\n hasChild: " + hasChild
                + ",\n hasSize: " + hasSize
                + ",\n createdAt: " + createdAt
                + ",\n sizes: " + sizes.size()
                + ",\n brands: " + brands.size()
                + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(hasBrand);
        dest.writeInt(hasName);
        dest.writeString(parentId);
        dest.writeInt(hasChild);
        dest.writeInt(hasSize);
        dest.writeString(createdAt);
        dest.writeList(sizes);
        dest.writeList(brands);
    }

}
