package com.coho.moki.data.model;

/**
 * Created by trung on 31/10/2017.
 */

public class Brand {
    private String brandId;
    private String brandName;

    public Brand(String brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
