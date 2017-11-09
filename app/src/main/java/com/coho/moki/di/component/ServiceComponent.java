package com.coho.moki.di.component;

import com.coho.moki.di.module.ActivityModule;
import com.coho.moki.di.module.ServiceModule;
import com.coho.moki.service.LoginService;
import com.coho.moki.service.LoginServiceImpl;
import com.coho.moki.service.SearchService;
import com.coho.moki.service.SizeService;
import com.coho.moki.ui.login.LoginPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by trung on 06/10/2017.
 */

@Component(modules = ServiceModule.class)
public interface ServiceComponent {
    LoginService getLoginService();

    SearchService getSearchService();

    SizeService getSizeService();
}
