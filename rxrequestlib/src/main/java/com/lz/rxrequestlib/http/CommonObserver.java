package com.lz.rxrequestlib.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * -------- 日期 ---------- 维护人 ------------ 变更内容 --------
 * 2017/12/26	9:24	     刘泽			  公用的订阅处理
 */
public abstract class CommonObserver<T> implements Observer<T> {




    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        String message = ApiException.handleException(t).getLocalizedMessage();
        onError(message);
    }

    @Override
    public void onComplete() {
    }


    protected void onError(String mes) {

    }
}