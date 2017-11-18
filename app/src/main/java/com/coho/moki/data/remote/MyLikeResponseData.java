package com.coho.moki.data.remote;

/**
 * Created by trung on 18/11/2017.
 */

public class MyLikeResponseData {

    private String id;
    private String name;
    private int price;
    private String image;

    public MyLikeResponseData(String id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
