package com.coho.moki.data.remote;

/**
 * Created by trung on 17/10/2017.
 */

public class ProductSmallResponceData {
    private String id;
    private String name;
    private String image;
    private String price;
    private String pricePercent;
    private String brand;
    private String described;
    private String created;
    private String like;
    private String comment;
    private String isLiked;
    private String isBlocked;
    private String canEdit;
    private String banned;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(String canEdit) {
        this.canEdit = canEdit;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public SellerResponceData getSeller() {
        return seller;
    }

    public void setSeller(SellerResponceData seller) {
        this.seller = seller;
    }
}
