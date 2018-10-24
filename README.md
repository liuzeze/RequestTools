# RequestTools
包含网络请求和  多线程断点续传文件下载

## 网络请求 
```    
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
                        });'

```

## 文件下载
```

 RxRequestUtils.downLoad(url, "filename", "filepath",
                        new Consumer<DownParamBean>() {
                            @Override
                            public void accept(@NonNull DownParamBean downParamBean) {
                                if (downParamBean.isCancle()) {
                                    mProgressBar.setProgress(0);
                                }
                                mProgressBar.setProgress(downParamBean.progress);
                            }
                        });
```
![image](https://github.com/liuzeze/RequestTools/blob/master/GIF.gif)  
