package com.coho.moki.data.remote;

/**
 * Created by tonquangtu on 14/11/2017.
 */

public class ProductCons {

    private String name;

    private int price;

    private String image;

    private String sellerId;

    public ProductCons(String name, int price, String image, String sellerId) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.sellerId = sellerId;
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
