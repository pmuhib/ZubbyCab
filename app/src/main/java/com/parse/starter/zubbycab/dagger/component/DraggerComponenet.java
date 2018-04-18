package com.parse.starter.zubbycab.dagger.component;

import com.parse.starter.zubbycab.dagger.module.DraggerDownloaderModule;
import com.parse.starter.zubbycab.utils.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Omar on 04/06/2016.
 */
@Singleton
@Component(modules = DraggerDownloaderModule.class)
public interface DraggerComponenet {
    void inject(BaseActivity baseActivity);
}
