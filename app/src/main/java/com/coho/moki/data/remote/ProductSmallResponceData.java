package com.coho.moki.data.remote;

import java.util.List;

/**
 * Created by trung on 17/10/2017.
 */

public class ProductSmallResponceData {
    private String id;
    private String name;
    private List<String> image;
    private int price;
    private int pricePercent;
    private List<BrandResponceData> brand;
    private String described;
    private String created;
    private int like;
    private int comment;
    private int isLiked;
    private int isBlocked;
    private int canEdit;
    private int banned;
    private SellerResponceData seller;

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

    public int getPricePercent() {
        return pricePercent;
    }

    public void setPricePercent(int pricePercent) {
        this.pricePercent = pricePercent;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public int getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(int canEdit) {
        this.canEdit = canEdit;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public List<BrandResponceData> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandResponceData> brand) {
        this.brand = brand;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public SellerResponceData getSeller() {
        return seller;
    }

    public void setSeller(SellerResponceData seller) {
        this.seller = seller;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
