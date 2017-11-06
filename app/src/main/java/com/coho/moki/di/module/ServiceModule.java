package com.coho.moki.di.module;

import com.coho.moki.service.LoginService;
import com.coho.moki.service.LoginServiceImpl;
import com.coho.moki.service.ProductDetailService;
import com.coho.moki.service.ProductDetailServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by trung on 06/10/2017.
 */

@Module
public class ServiceModule {

    @Provides
    LoginService provideLoginService(LoginServiceImpl loginService){
        return loginService;
    }

    @Provides
    ProductDetailService provideProductDetailService(ProductDetailServiceImpl productDetailService) {
        return productDetailService;
    }

}
