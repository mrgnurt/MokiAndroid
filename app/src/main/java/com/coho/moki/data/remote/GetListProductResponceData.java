package com.coho.moki.data.remote;

import java.util.ArrayList;

/**
 * Created by trung on 17/10/2017.
 */

public class GetListProductResponceData {
    private ArrayList<ProductSmallResponceData> productSmallResponceDatas;
    private String newItems;
    private String lastId;

    public ArrayList<ProductSmallResponceData> getProductSmallResponceDatas() {
        return productSmallResponceDatas;
    }

    public void setProductSmallResponceDatas(ArrayList<ProductSmallResponceData> productSmallResponceDatas) {
        this.productSmallResponceDatas = productSmallResponceDatas;
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
