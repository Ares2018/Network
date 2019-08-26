package com.core.network.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.core.network.ApiManager;
import com.core.network.cache.CachePolicy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 参数设置工具
 *
 * @author a_liYa
 * @date 2017/12/26 22:21.
 */
public final class ParamsBuilder {

    public static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 构建 GET 请求参数
     *
     * @param paramsMap 参数Map
     * @param url       url
     * @return 带参数的url
     */
    @Nullable
    public static String buildGet(Map<String, Object> paramsMap, String url) {
        StringBuilder sb = null;
        if (paramsMap != null && !paramsMap.isEmpty()) {
            sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (TextUtils.isEmpty(entry.getKey()))
                    continue;
                sb.append(entry.getKey()).append('=');

                Object value = entry.getValue();
                try {
                    sb.append((value == null || TextUtils.isEmpty(value.toString())) ? "" :
                            URLEncoder.encode(value.toString(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append('&');
            }
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        if (sb != null && sb.length() > 0) {
            url += '?' + sb.toString();
        }
        return url;
    }

    /**
     * 构建 POST 请求参数
     *
     * @param paramsMap 参数Map
     * @return RequestBody
     */
    @NonNull
    public static RequestBody buildPost(Map<String, Object> paramsMap) {
        return RequestBody.create(
                MEDIA_JSON,
                ApiManager.getApiConfig().getJsonParse().onJsonString(paramsMap));
    }

    /**
     * 构建 POST Form 请求参数
     *
     * @param paramsMap 参数Map
     * @return RequestBody
     */
    @NonNull
    public static RequestBody buildPostForm(Map<String, Object> paramsMap) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (paramsMap != null && !paramsMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (TextUtils.isEmpty(entry.getKey()))
                    continue;

                Object value = entry.getValue();
                formBodyBuilder.add(entry.getKey(), value == null ? "" : value.toString());
            }
        }
        return formBodyBuilder.build();
    }

    /**
     * 构建 上传文件 请求参数
     *
     * @param paramsMap 参数Map
     * @param filesMap  文件Map
     * @return RequestBody
     */
    @NonNull
    public static RequestBody buildUpload(Map<String, Object> paramsMap,
                                          Map<String, String> filesMap) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (paramsMap != null && !paramsMap.isEmpty()) { // 表单内容
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (TextUtils.isEmpty(entry.getKey()))
                    continue;

                Object value = entry.getValue();
                multipartBodyBuilder.addFormDataPart(entry.getKey(), value == null ? "" :
                        value.toString()); // 防止npe
            }
        }

        if (filesMap != null && !filesMap.isEmpty()) { // 上传文件
            for (Map.Entry<String, String> entry : filesMap.entrySet()) {
                if (TextUtils.isEmpty(entry.getKey()))
                    continue;

                String value = entry.getValue();
                if (!TextUtils.isEmpty(value)) {
                    File file = new File(value);
                    if (file.exists()) {
                        final String fileName = file.getName();
                        multipartBodyBuilder.addFormDataPart(
                                entry.getKey(),
                                fileName,
                                RequestBody.create(createMediaType(fileName), file));
                    }
                }
            }
        }
        return multipartBodyBuilder.build();
    }

    /**
     * 构建 header
     *
     * @param request     request
     * @param headers     header map
     * @param cachePolicy 缓存策略
     */
    public static void buildHeader(Request.Builder request,
                                   Map<String, Set<String>> headers,
                                   CachePolicy cachePolicy) {
        if (request == null) return;

        if (headers != null && !headers.isEmpty()) { // 设置Header
            for (Map.Entry<String, Set<String>> entry : headers.entrySet()) {
                String name = entry.getKey();
                Set<String> values = entry.getValue();
                if (values != null) {
                    for (String value : values) {
                        request.addHeader(name, value);
                    }
                }
            }
        }

        // 封装的缓存策略
        if (cachePolicy != null) {
            if (!TextUtils.isEmpty(cachePolicy.cachePolicy)) {
                request.header(CachePolicy.headerKey(), cachePolicy.cachePolicy);
            }
            if (cachePolicy.cacheMaxAge > CachePolicy.MAX_AGE_NO_VALUE) {
                request.header(CachePolicy.maxAgeKey(), String.valueOf(cachePolicy.cacheMaxAge));
            }
        }
    }

    private static MediaType createMediaType(String filename) {
        return MediaType.parse(URLConnection.getFileNameMap().getContentTypeFor(filename));
    }
}
