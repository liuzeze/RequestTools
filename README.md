# RequestTools
包含网络请求和  多线程断点续传文件下载

## 网络请求 
'          RxRequestUtils
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


![image](https://github.com/liuzeze/RequestTools/blob/master/GIF.gif)  

