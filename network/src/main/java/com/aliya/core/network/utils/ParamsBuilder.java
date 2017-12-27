package com.aliya.core.network.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 参数设置工具
 *
 * @author a_liYa
 * @date 2017/12/26 22:21.
 */
public final class ParamsBuilder {

    public static @Nullable String buildGet(Map<String, Object> paramsMap, String url) {
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

    public static @NonNull FormBody buildPost(Map<String, Object> paramsMap) {
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

    public static @NonNull MultipartBody buildUpload(Map<String, Object> paramsMap,
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
                        multipartBodyBuilder.addFormDataPart(
                                entry.getKey(),
                                file.getName(),
                                RequestBody.create(null, file));
                        // MediaType.parse("image/jpeg")
                    }
                }
            }
        }
        return multipartBodyBuilder.build();
    }

}
