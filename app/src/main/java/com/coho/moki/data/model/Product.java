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

    private String numLike;

    private String numComment;

    public Product(String productId, String name, String imageUrl, String price, String pricePercent, String brand, String description, String numLike, String numComment) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.pricePercent = pricePercent;
        this.brand = brand;
        this.description = description;
        this.numLike = numLike;
        this.numComment = numComment;
    }

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

    public String getNumLike() {
        return numLike;
    }

    public void setNumLike(String numLike) {
        this.numLike = numLike;
    }

    public String getNumComment() {
        return numComment;
    }

    public void setNumComment(String numComment) {
        this.numComment = numComment;
    }
}
