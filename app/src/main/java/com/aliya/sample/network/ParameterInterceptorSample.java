package com.aliya.sample.network;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 参数处理拦截器
 *
 * @author a_liYa
 * @date 2019/3/27 11:32.
 */
public class ParameterInterceptorSample implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        if ("GET".equalsIgnoreCase(request.method())) {

        } else if ("POST".equalsIgnoreCase(request.method())) {
            String bodyString = requestBodyString(request.body());
            HashMap map = JSON.parseObject(bodyString, HashMap.class);
            map.put("key", "value");
            JSON.toJSONString(map);
            builder.post(RequestBody.create(MEDIA_JSON, bodyString));
        }
        return chain.proceed(builder.build());
    }

    /**
     * 从RequestBody中获取body参数
     *
     * @param body RequestBody
     * @return 参数，get请求返回"{}"
     */
    private String requestBodyString(RequestBody body) {
        if (body != null) {
            Buffer buffer = new Buffer();
            try {
                body.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                    if (charset == null)
                        charset = UTF8;
                }
                return buffer.readString(charset);
            } catch (Exception e) {
            } finally {
                try {
                    buffer.close();
                } catch (Exception e) {
                }
            }
        }
        return "{}";
    }

}
