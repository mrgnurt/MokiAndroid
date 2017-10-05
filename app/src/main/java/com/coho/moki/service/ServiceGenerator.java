package com.coho.moki.service;

import com.coho.moki.data.constant.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by trung on 05/10/2017.
 */

public class ServiceGenerator {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(AppConstant.BASEURL_TAG)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(120, TimeUnit.SECONDS)
                            .readTimeout(120, TimeUnit.SECONDS)
                            .build());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
