package com.lz.rxrequestplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lz.rxrequestlib.RxRequestUtils;
import com.lz.rxrequestlib.download.DownParamBean;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-24       创建class
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    private final Elements mLinks;
    private final Activity mFileListActivity;
    private View mInflate;

    public FileAdapter(Elements links, Activity fileListActivity) {
        mLinks = links;
        mFileListActivity = fileListActivity;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflate = View.inflate(viewGroup.getContext(), R.layout.item_file, null);
        return new FileViewHolder(mInflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder viewHolder, int i) {
        final Element element = mLinks.get(i);
        final String href = element.attr("href");
        if (element.text().startsWith("Up To [/")) {
            viewHolder.mItemView.setText(element.text().replace("Up To", "上一页"));
        } else {
            viewHolder.mItemView.setText(element.text());
        }
        String decode = "";
        try {
            decode = URLDecoder.decode(href, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            decode = href;
        }
        final File seafile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "seafile" + File.separator
                + decode.replace(element.text(), ""));
        if (!TextUtils.isEmpty(href)) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (element.text().startsWith("Up To [/")) {
                        mFileListActivity.finish();
                        return;
                    }

                   /* final String value = href;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request.Builder builder = new Request.Builder();
                    builder.url(App.mBaseUrl + href);
                    okHttpClient.newCall(builder.build()).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            response.body();
                            if (!response.header("Content-Type").contains("text/html")) {
                                RxRequestUtils.downLoad(response.request().url().toString(), element.text(), seafile.getAbsolutePath(),
                                        new Consumer<DownParamBean>() {
                                            @Override
                                            public void accept(@io.reactivex.annotations.NonNull DownParamBean downParamBean) {
                                                viewHolder.mProgressView.setProgress(downParamBean.progress);
                                            }
                                        });
                            } else {

                                Intent mIntent = new Intent(mFileListActivity, FileListActivity.class);
                                mIntent.putExtra("url", value);
                                mFileListActivity.startActivity(mIntent);
                            }
                        }
                    });*/

                    if (href.endsWith(".apk") ||
                            href.endsWith(".docx") ||
                            href.endsWith(".xlsx") ||
                            href.endsWith(".word") ||
                            href.endsWith(".md") ||
                            href.endsWith(".jar") ||
                            href.endsWith(".zip") ||
                            href.endsWith(".db") ||
                            href.endsWith(".rar") ||
                            href.endsWith(".txt")
                            ) {
                        RxRequestUtils.downLoad(App.mBaseUrl + href, element.text(), seafile.getAbsolutePath(),
                                new Consumer<DownParamBean>() {
                                    @Override
                                    public void accept(@io.reactivex.annotations.NonNull DownParamBean downParamBean) {
                                        viewHolder.mProgressView.setProgress(downParamBean.progress);
                                    }
                                });
                    } else {

                        Intent mIntent = new Intent(mFileListActivity, FileListActivity.class);
                        mIntent.putExtra("url", href);
                        mFileListActivity.startActivity(mIntent);
                    }


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        TextView mItemView;
        ProgressView mProgressView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = (TextView) itemView.findViewById(R.id.file_name);
            mProgressView = itemView.findViewById(R.id.progress);
        }
    }
}
