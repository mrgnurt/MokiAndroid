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

package com.coho.moki.di.component;

import com.coho.moki.di.module.ActivityModule;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.login.LoginPresenterImpl;
import com.coho.moki.ui.product.ProductCommentActivity;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.ui.main_search.MainSearchActivity;
import com.coho.moki.ui.product_search.ProductSearchActivity;
import com.coho.moki.ui.size.SizeActivity;
import com.coho.moki.ui.splash.SplashActivity;
import com.coho.moki.ui.user.UserInfoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
@Component(dependencies = ServiceComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(MainSearchActivity activity);

    void inject(ProductSearchActivity activity);

    void inject(SizeActivity activity);

    void inject(ProductDetailActivity activity);

    void inject(ProductCommentActivity activity);

    void inject(UserInfoActivity activity);

}
