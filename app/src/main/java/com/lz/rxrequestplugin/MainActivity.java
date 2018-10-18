package com.lz.rxrequestplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lz.rxrequestlib.http.CommonObserver;
import com.lz.rxrequestlib.RxRequestUtils;
import com.lz.rxrequestlib.http.Transformer;
import com.lz.rxrequestlib.download.DownParamBean;
import com.lz.rxrequestlib.RxDownloadManager;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mToRequest;
    private Button mBtDownLoad;
    private Button mBtPause;
    private Button mBtCancle;
    private TextView mContent;
    private ProgressView mProgressBar;
//    String url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk";
    String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToRequest = findViewById(R.id.bt_torequest);
        mBtDownLoad = findViewById(R.id.bt_download);
        mBtPause = findViewById(R.id.bt_pause);
        mBtCancle = findViewById(R.id.bt_cancle);
        mContent = findViewById(R.id.tv_content);
        mProgressBar = findViewById(R.id.ProgressBar);
        mBtDownLoad.setOnClickListener(this);
        mBtPause.setOnClickListener(this);
        mBtCancle.setOnClickListener(this);
        mToRequest.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_download:
                RxRequestUtils.downLoad(url, "", "",
                        new Consumer<DownParamBean>() {
                            @Override
                            public void accept(@NonNull DownParamBean downParamBean) {
                                if (downParamBean.isCancle()) {
                                    mProgressBar.setProgress(0);
                                }
                                mProgressBar.setProgress(downParamBean.progress);
                            }
                        });
                break;
            case R.id.bt_pause:
                RxRequestUtils.downLoadPause(url);
                break;
            case R.id.bt_cancle:
                RxRequestUtils.downLoadCancel(url);

                break;
            case R.id.bt_torequest:
                RxRequestUtils
                        .create(RequestApi.class)
                        .getNews()
                        .compose(Transformer.<String>switchSchedulers())
                        .subscribe(new CommonObserver<String>() {
                            @Override
                            public void onNext(String s) {
                                JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                mContent.setText(gson.toJson(asJsonObject));
                            }
                        });
                break;
            default:
                break;
        }
    }
}
