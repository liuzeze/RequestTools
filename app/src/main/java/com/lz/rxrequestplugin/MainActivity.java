package com.lz.rxrequestplugin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lz.rxrequestlib.http.CommonObserver;
import com.lz.rxrequestlib.RxRequestUtils;
import com.lz.rxrequestlib.http.Transformer;
import com.lz.rxrequestlib.download.DownParamBean;
import com.lz.rxrequestlib.RxDownloadManager;
import com.lz.rxrequestlib.upload.UploadRetrofit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mToRequest;
    private Button mBtDownLoad;
    private Button mBtPause;
    private Button mBtCancle;
    private TextView mContent;
    private ProgressView mProgressBar;
    //    String url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk";
    String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private View mGetFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetFileList = findViewById(R.id.bt_jump);
        mToRequest = findViewById(R.id.bt_torequest);
        mBtDownLoad = findViewById(R.id.bt_download);
        mBtPause = findViewById(R.id.bt_pause);
        mBtCancle = findViewById(R.id.bt_cancle);
        mContent = findViewById(R.id.tv_content);
        mProgressBar = findViewById(R.id.ProgressBar);
        findViewById(R.id.bt_upload).setOnClickListener(this);
        mBtDownLoad.setOnClickListener(this);
        mBtPause.setOnClickListener(this);
        mBtCancle.setOnClickListener(this);
        mToRequest.setOnClickListener(this);
        mGetFileList.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String file = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "Download";
        String fileName = "big_buck_bunny.mp4";
        switch (view.getId()) {
            case R.id.bt_download:
                RxRequestUtils.downLoad(url, fileName, file,
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
            case R.id.bt_upload:
                RxRequestUtils.uploadFile("http://172.17.151.210:8080/springUpload", file, fileName)
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                try {
                                    String string = responseBody.string();
                                    Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

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
            case R.id.bt_jump:
                Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                intent.putExtra("url", "");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
