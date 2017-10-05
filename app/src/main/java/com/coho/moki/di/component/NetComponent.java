package com.coho.moki.di.component;

import com.coho.moki.di.module.ApplicationModule;
import com.coho.moki.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by trung on 04/10/2017.
 */

@Singleton
@Component(modules = {NetModule.class, ApplicationModule.class})
public interface NetComponent {
    Retrofit retrofit();
}
