package com.coho.moki.data.remote;

import java.util.ArrayList;

/**
 * Created by trung on 17/10/2017.
 */

public class GetListProductResponceData {
    private ArrayList<ProductSmallResponceData> products;
    private String newItems;
    private String lastId;

    public ArrayList<ProductSmallResponceData> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductSmallResponceData> productSmallResponceDatas) {
        this.products = productSmallResponceDatas;
    }

    public String getNewItems() {
        return newItems;
    }

    public void setNewItems(String newItems) {
        this.newItems = newItems;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }
}
