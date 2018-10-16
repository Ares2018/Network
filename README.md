# Network

网络相关组件的封装

### 添加依赖

```
dependencies {
    implementation 'com.core:network:0.0.1'
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.squareup.okhttp3:okhttp:3.9.1'
}
```

### 用法

 ApiConfig 网络配置相关类

* url拼接/转换 - 接口 : UrlTransform

 实现示例
 ```
 public class AppUrlTransform implements UrlTransform {

     @Override
     public String onUrlTransform(String api) {
         if (api != null && api.startsWith("/")) {
             return getBaseUrl() + api;
         }
         return api;
     }

     private String getBaseUrl() {
         return "https://apibeta.8531.cn";
     }

 }
 ```

* Json解析 - 接口 : JsonParse

 实现示例
 ```
 public class AppJsonParse implements JsonParse {

     @Override
     public <T> T onJsonParse(String text, Type type) {
         return JSON.parseObject(text, type);
     }

 }
 ```

* 解析响应体 - 接口 ：ParseResponse

默认实现
```
public class ParseResponseImpl implements ParseResponse {

    @Override
    public <T> void onParseResponse(@NonNull Response response, @NonNull AgentCallback<T>
            callback, @NonNull ApiTask apiTask) {
        if (200 == response.code()) { // 请求成功
            try {
                handleBody(response, callback, apiTask);
            } catch (IOException e) {
                ApiManager.getApiConfig()
                        .getExceptionTransform().onExceptionTransform(e, callback);
            }
        } else {
            callback.onError(response.code(), "HTTP status code != 200");
        }
    }

    private <T> void handleBody(@NonNull Response response, @NonNull AgentCallback<T> callback,
                                @NonNull ApiTask apiTask) throws IOException {
        String body = response.body().string();

        T data = null;
        ApiCallback<T> apiCallback = apiTask.getCallback();
        if (apiCallback != null) {
            Type typeOf = GenericUtils.getGenericType(apiCallback.getClass());
            if (typeOf == null) {
                data = null; // 泛型未声明或者不合法
                if (ApiManager.isDebuggable()) {
                    Log.e(ApiManager.LOG_TAG, apiCallback.getClass().getName() + "类型参数未声明");
                }
            } else if (typeOf == Void.class) {
                data = null;
            } else if (typeOf == String.class) {
                data = (T) body;
            } else {
                data = ApiManager.getApiConfig().getJsonParse().onJsonParse(body, typeOf);
            }
        }
        callback.onSuccess(data);
    }

}
```

* 延迟HttpClient加载器 - 接口 ： LazyClientLoader

实现示例
```
public class AppClientLoader implements LazyClientLoader {

    @NonNull
    @Override
    public OkHttpClient.Builder newBuilder() {
        SSLSocketManager sslSM = new SSLSocketManager();
        return new OkHttpClient.Builder()
                .addInterceptor(new HeadersInterceptor()) // 设置Cookie拦截器
                .retryOnConnectionFailure(true)
                .sslSocketFactory(sslSM.getSSLSocketFactory(), sslSM.getX509TrustManager())
                .hostnameVerifier(sslSM.getHostnameVerifier())
                .connectTimeout(5, TimeUnit.SECONDS)  // 设置网络超时 - 连接
                .readTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 读
                .writeTimeout(20, TimeUnit.SECONDS) // 设置网络超时 - 写
                .cache(getCache()); // 缓存设置;
    }

    public static final String CACHE_FILENAME = "HttpCache";
    public static final int CACHE_MAX_SIZE = 10 * 1024 * 1024; // 10M

    /**
     * 获取缓存设置
     *
     * @return 缓存对象
     */
    private static Cache getCache() {
        // /data/user/0/包名/cache
        File httpCacheDirectory = new File(ApiManager.getContext().getCacheDir(), CACHE_FILENAME);
        return new Cache(httpCacheDirectory, CACHE_MAX_SIZE); // 参一:缓存目录; 参二:缓存最大容量
    }

}
```


> 初始化

```
ApiManager.init(this, ApiConfig.newBuilder()
        .urlTransform(new AppUrlTransform())
        .addApiPreFilter(new AppApiPreFilter())
        .lazyClientLoader(new AppClientLoader())
        .jsonParse(new AppJsonParse()));
```
