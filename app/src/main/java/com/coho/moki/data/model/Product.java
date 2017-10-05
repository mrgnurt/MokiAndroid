package com.coho.moki.data.model;

/**
 * Created by trung on 27/09/2017.
 */

public class Product {

    private String productId;

    private String name;

    private String imageUrl;

    private String price;

    private String pricePercent;

    private String brand;

    private String description;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(String pricePercent) {
        this.pricePercent = pricePercent;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
