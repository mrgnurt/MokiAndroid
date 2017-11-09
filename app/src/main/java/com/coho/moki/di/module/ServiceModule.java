package com.coho.moki.di.module;

import com.coho.moki.service.LoginService;
import com.coho.moki.service.LoginServiceImpl;
import com.coho.moki.service.SearchService;
import com.coho.moki.service.SearchServiceImpl;
import com.coho.moki.service.SizeService;
import com.coho.moki.service.SizeServiceImpl;

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
    SearchService provideSearchService(SearchServiceImpl searchService){
        return searchService;
    }

    @Provides
    SizeService provideSizeService(SizeServiceImpl sizeService){
        return sizeService;
    }
}
