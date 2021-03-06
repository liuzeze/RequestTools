package com.lz.rxrequestlib;

import android.content.Context;

import com.lz.rxrequestlib.download.DownLoadFactory;
import com.lz.rxrequestlib.download.DownParamBean;
import com.lz.rxrequestlib.http.HttpConfigFactory;
import com.lz.rxrequestlib.http.RetrofitFactory;
import com.lz.rxrequestlib.upload.UploadRetrofit;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-16       创建class
 */
public class RxRequestUtils {

    private static RxRequestUtils sRxRquestUtils;


    public static RxRequestUtils getInstance() {
        if (sRxRquestUtils == null) {
            synchronized (RxRequestUtils.class) {
                if (sRxRquestUtils == null) {
                    sRxRquestUtils = new RxRequestUtils();
                }
            }
        }
        return sRxRquestUtils;
    }


    public void init(Context context) {
        HttpConfigFactory.getInstance().initConfig(context);
        RetrofitFactory.getInstance();
        DownLoadFactory.getInstance();
        UploadRetrofit.getInstance();
    }


    public static <T> T create(Class<T> apiClass) {
        return RetrofitFactory
                .getInstance()
                .getRetrofitClient()
                .build()
                .create(apiClass);

    }


    public static void downLoad(String url, String fileName, String filePath, Consumer<DownParamBean> consumer) {
        RxDownloadManager.getInstance().downLoad(url, fileName, filePath, consumer);
    }

    public static void downLoadPause(String url) {
        RxDownloadManager.getInstance().pause(url);
    }

    public static void downLoadCancel(String url) {
        RxDownloadManager.getInstance().cancel(url);
    }

    public static Observable<ResponseBody> uploadFile(String url, String file, String fileName) {
        return UploadRetrofit.uploadImage(url,
                file + File.separator + fileName);


    }

}