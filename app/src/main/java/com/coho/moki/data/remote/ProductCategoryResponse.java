package com.coho.moki.data.remote;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/15/2017.
 */

public class ProductCategoryResponse {

    private String id;
    private String name;
    private Integer hasBrand;
    private Integer hasName;
    private String parentId;
    private Integer hasChild;
    private Integer hasSize;
    private String createdAt;
    private List<ProductDetailResponse.Size> sizes;
    private List<ProductDetailResponse.Brand> brands;

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

    public Integer getHasBrand() {
        return hasBrand;
    }

    public void setHasBrand(Integer hasBrand) {
        this.hasBrand = hasBrand;
    }

    public Integer getHasName() {
        return hasName;
    }

    public void setHasName(Integer hasName) {
        this.hasName = hasName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getHasChild() {
        return hasChild;
    }

    public void setHasChild(Integer hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getHasSize() {
        return hasSize;
    }

    public void setHasSize(Integer hasSize) {
        this.hasSize = hasSize;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<ProductDetailResponse.Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductDetailResponse.Size> sizes) {
        this.sizes = sizes;
    }

    public List<ProductDetailResponse.Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<ProductDetailResponse.Brand> brands) {
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

}
