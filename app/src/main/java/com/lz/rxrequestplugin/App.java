package com.lz.rxrequestplugin;

import android.app.Application;

import com.lz.rxrequestlib.RxRequestUtils;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-17       创建class
 */
public class App extends Application {
    //tomcat文件路径 地址
    public static String mBaseUrl = "http://172.17.151.210:8080";

    @Override
    public void onCreate() {
        super.onCreate();
        RxRequestUtils.getInstance().init(this);

    }
}
