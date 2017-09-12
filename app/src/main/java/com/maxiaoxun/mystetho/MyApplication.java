package com.maxiaoxun.mystetho;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by maxiaoxun on 17/9/12 13:38.
 * email maxiaoxun@meituan.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}
