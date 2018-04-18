package com.parse.starter.zubbycab.dagger.module;


import android.content.Context;


import com.parse.starter.zubbycab.dagger.model.DraggerModule;
import com.parse.starter.zubbycab.utils.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import retrofit2.Retrofit;

/**
 * Created by Omar on 04/06/2016.
 */
@Module
public class DraggerDownloaderModule {
    private Context context;

    public DraggerDownloaderModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    DraggerModule provideImageDownloader(Context context) {
        return new DraggerModule(context);
    }

    @Provides
    @Singleton
    Boolean checkInternet(Context context) {
        return false;
    }

    @Provides
    @Singleton
    ApiService apiService() {
        return null;
    }

    @Provides
    @Singleton
    Retrofit getClient() {
        return null;
    }


//    @Provides @Singleton
//    ApiService getApiService(){
//        return null;
//    }
}
