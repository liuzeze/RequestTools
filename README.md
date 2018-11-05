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

## 文件上传
```
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

```
![image](https://github.com/liuzeze/RequestTools/blob/master/GIF.gif)  

