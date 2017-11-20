package com.coho.moki.util;

import android.util.Log;

import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by hex0r on 11/2/15.
 */
public class APICacheUtils {

    private static APICacheUtils instance;
    private static Gson gson;

    public static APICacheUtils get() {
        if (instance == null){
            instance = new APICacheUtils();
        }
        return instance;
    }

    private APICacheUtils() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public void saveResult(Object obj, String key) {
        String json = gson.toJson(obj);
        SharedPrefUtils.putString(key, json);
    }

    public synchronized  <T> T getResult(String key, Class<T> clazz) {
        String json = SharedPrefUtils.getString(key, "");
        if (StringUtil.isEmpty(json)) {
            return null;
        } else {
            try{
                return gson.fromJson(json, clazz);
            } catch (Exception ex){
                return null;
            }
        }
    }

    public synchronized ArrayList<ProductCategoryResponse> getCategories(String key, Type clazz) {
        String json = SharedPrefUtils.getString(key, "");
        if (StringUtil.isEmpty(json)) {
            return null;
        } else {
            try{
                return gson.fromJson(json, clazz);
            } catch (Exception ex){
                return null;
            }
        }
    }

    public synchronized ArrayList<ProductSmallResponceData> getProducts(String key, Type clazz) {
        String json = SharedPrefUtils.getString(key, "");
        if (StringUtil.isEmpty(json)) {
            return null;
        } else {
            try{
                return gson.fromJson(json, clazz);
            } catch (Exception ex){
                return null;
            }
        }
    }

}
