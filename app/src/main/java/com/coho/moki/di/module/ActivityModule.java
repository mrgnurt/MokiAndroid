/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.coho.moki.di.module;

import com.coho.moki.service.LoginService;
import com.coho.moki.service.LoginServiceImpl;
import com.coho.moki.ui.login.LoginPresenter;
import com.coho.moki.ui.login.LoginPresenterImpl;
import com.coho.moki.ui.product.ProductDetailPresenter;
import com.coho.moki.ui.product.ProductDetailPresenterImpl;
import com.coho.moki.ui.splash.SplashPresenter;
import com.coho.moki.ui.splash.SplashPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    @Singleton
    @Provides
    SplashPresenter provideSplashPresenter(SplashPresenterImpl splashPresenter) {
        return splashPresenter;
    }

    @Singleton
    @Provides
    LoginPresenter provideLoginPresenter(LoginPresenterImpl loginPresenter){
        return loginPresenter;
    }

    @Singleton
    @Provides
    ProductDetailPresenter provideProductDetailPresenter(ProductDetailPresenterImpl productDetailPresenter) {
        return productDetailPresenter;
    }

}
