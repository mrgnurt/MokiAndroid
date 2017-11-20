package com.coho.moki.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.coho.moki.BaseApp;
import com.coho.moki.api.ProductAPI;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.data.remote.ProductResponseData;
import com.coho.moki.data.remote.UserProductResponseData;
import com.coho.moki.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by trung on 17/10/2017.
 */

public class ProductServiceImpl implements ProductService {

    @Override
    public void getListProduct(String token, String categoryId, String campaignId, String lastId,
                               String index, int count, final ResponseListener<GetListProductResponceData> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.CATEGORYID_TAG, categoryId);
        data.put(AppConstant.CAMPAIGNID_TAG, campaignId);
        data.put(AppConstant.LASTID_TAG, lastId);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, ((Integer)count).toString());

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<GetListProductResponceData>> call =  service.callGetListProduct(data);
        Log.d("trung","abc");
        call.enqueue(new Callback<BaseResponse<GetListProductResponceData>>() {
            @Override
            public void onResponse(Call<BaseResponse<GetListProductResponceData>> call, Response<BaseResponse<GetListProductResponceData>> response) {

                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){

                    listener.onSuccess(response.body().getData());
                }
                else{

                    listener.onFailure(response.body().getMessage() + " get list product");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<GetListProductResponceData>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get list product");

            }
        });
    }

    @Override
    public void getProductOfUser(String token, int index, int count, String userId, String keyword, String categoryId, final ResponseListener<ArrayList<UserProductResponseData>> listener) {
        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN_TAG, token);
        data.put(AppConstant.INDEX, index);
        data.put(AppConstant.COUNT_TAG, count);
        data.put(AppConstant.USERID_TAG, userId);
        data.put(AppConstant.KEYWORD_TAG, keyword);
        data.put(AppConstant.CATEGORY_TAG, categoryId);

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<ArrayList<UserProductResponseData>>> call =  service.callGetUserProduct(data);
        call.enqueue(new Callback<BaseResponse<ArrayList<UserProductResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArrayList<UserProductResponseData>>> call, Response<BaseResponse<ArrayList<UserProductResponseData>>> response) {
                int code = response.body().getCode();
                if (code == ResponseCode.OK.code){

                    listener.onSuccess(response.body().getData());
                }
                else{

                    listener.onFailure(response.body().getMessage() + " get list product");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<ArrayList<UserProductResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage() + " get list product");
            }

        });
    }

    @Override
    public void getMyLikeProduct(String token, int index, int count, final ResponseListener<List<MyLikeResponseData>> listener) {

        Map<String, Object> data = new HashMap<>();
        data.put(AppConstant.TOKEN, token);
        data.put(AppConstant.INDEX_TAG, index);
        data.put(AppConstant.COUNT_TAG, count);

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<List<MyLikeResponseData>>> call = service.callGetMyLikeProduct(data);
        call.enqueue(new Callback<BaseResponse<List<MyLikeResponseData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MyLikeResponseData>>> call, Response<BaseResponse<List<MyLikeResponseData>>> response) {
                BaseResponse<List<MyLikeResponseData>> bodyResponse = response.body();

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MyLikeResponseData>>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });



    }

    @Override
    public void addProduct(String token, String name, int price, String productSizeId,
                           String brandId, String categoryId, List<Uri> image,
                           Uri video, Uri thumb, String described, String shipsFrom,
                           List<Integer> shipsFromId, int condition, List<Integer> dimension, String weight,
                           final ResponseListener<ProductResponseData> listener) {
        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.TOKEN, token);
        map.put(AppConstant.NAME, name);
        map.put(AppConstant.PRICE, price);
        map.put(AppConstant.PRODUCT_SIZE_ID_TAG, productSizeId);
        map.put(AppConstant.BRANDID, brandId);
        map.put(AppConstant.CATEGORYID_TAG, categoryId);
        map.put(AppConstant.DESCRIBED, described);
        map.put(AppConstant.SHIPSFROM, shipsFrom);
        map.put(AppConstant.SHIPSFROM_ID, shipsFromId);
        map.put(AppConstant.CONDITION, condition);
        map.put(AppConstant.DIMENSION, dimension);
        map.put(AppConstant.WEIGHT, weight);

//        Get images
        ArrayList<String> imagesR = new ArrayList<>();
        if (image != null) {
            for (int i = 0; i < image.size(); i++) {
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(BaseApp.getContext().getContentResolver(), image.get(i));
//                    bm = Utils.getScaleImage(BaseApp.getContext(), image.get(i));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    imagesR.add(encodedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            map.put(AppConstant.IMAGE, imagesR);
        } else {
            File fileVideo = new File(video.getPath());
            RequestBody videoR = RequestBody.create(MediaType.parse("video/*"), fileVideo);

            File fileThumb = new File(thumb.getPath());
            RequestBody thumbR = RequestBody.create(MediaType.parse("image/*"), fileThumb);
            map.put(AppConstant.VIDEO, videoR);
            map.put(AppConstant.THUMB, thumbR);
        }

        ProductAPI service = ServiceGenerator.createService(ProductAPI.class);
        Call<BaseResponse<ProductResponseData>> call = service.callAddProduct(map);
        call.enqueue(new Callback<BaseResponse<ProductResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ProductResponseData>> call, Response<BaseResponse<ProductResponseData>> response) {
                BaseResponse<ProductResponseData> bodyResponse = response.body();

                if (bodyResponse == null) {
                    listener.onFailure(AppConstant.NO_FETCH_DATA);
                    return;
                }

                if (response.code() != 200) {
                    if (response.code() == 401) {
                        listener.onFailure(AppConstant.UNAUTHENTICATED);
                    } else {
                        listener.onFailure(AppConstant.NO_FETCH_DATA);
                    }
                    return;
                }

                if (bodyResponse.getCode() != ResponseCode.OK.code) {
                    listener.onFailure(bodyResponse.getMessage());
                    return;
                }

                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<BaseResponse<ProductResponseData>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
