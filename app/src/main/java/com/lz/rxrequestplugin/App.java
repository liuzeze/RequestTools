package com.lz.rxrequestplugin;

import android.app.Application;

import com.lz.rxrequestlib.RxRequestUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-17       创建class
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxRequestUtils.getInstance().init(this);

    }
}
