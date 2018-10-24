package com.lz.rxrequestplugin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lz.rxrequestlib.RxRequestUtils;
import com.lz.rxrequestlib.http.CommonObserver;
import com.lz.rxrequestlib.http.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import io.reactivex.schedulers.Schedulers;

public class FileListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Elements mLinks = new Elements();
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        mUrl = App.mBaseUrl + getIntent().getStringExtra("url");
        initRecyclerView();
        RxRequestUtils
                .create(RequestApi.class)
                .getFilelist(mUrl)
                .compose(Transformer.<String>switchSchedulers())
                .observeOn(Schedulers.io())
                .subscribe(new CommonObserver<String>() {


                    @Override
                    public void onNext(String s) {
                        mLinks.clear();
                        Document doc = Jsoup.parse(s);
                        mLinks.addAll(doc.select("a[href]"));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new FileAdapter(mLinks, this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }
}
