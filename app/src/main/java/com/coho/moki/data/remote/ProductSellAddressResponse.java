package com.coho.moki.data.remote;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class ProductSellAddressResponse {
    private Integer provinceOrder;
    private String provinceName;
    private Integer districtOrder;
    private String districtName;
    private Integer townOrder;
    private String townName;

    public Integer getProvinceOrder() {
        return provinceOrder;
    }

    public void setProvinceOrder(Integer provinceOrder) {
        this.provinceOrder = provinceOrder;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getDistrictOrder() {
        return districtOrder;
    }

    public void setDistrictOrder(Integer districtOrder) {
        this.districtOrder = districtOrder;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getTownOrder() {
        return townOrder;
    }

    public void setTownOrder(Integer townOrder) {
        this.townOrder = townOrder;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
