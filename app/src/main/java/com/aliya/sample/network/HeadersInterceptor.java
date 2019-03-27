package com.aliya.sample.network;

import android.content.Context;
import android.os.Build;

import com.core.network.ApiManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义okhttp Cookies 过滤器
 *
 * @author a_liYa
 * @date 16/6/30 14:29.
 */
public class HeadersInterceptor implements Interceptor {

    /**
     * key X-SESSION-ID
     */
    public static final String KEY_SESSION_ID = "X-SESSION-ID";
    /**
     * key X-REQUEST-ID
     */
    public static final String KEY_REQUEST_ID = "X-REQUEST-ID";
    /**
     * key X-TIMESTAMP
     */
    public static final String KEY_TIMESTAMP = "X-TIMESTAMP";
    /**
     * key X-SIGNATURE
     */
    public static final String KEY_SIGNATURE = "X-SIGNATURE";
    /**
     * key User-Agent
     */
    public static final String KEY_USER_AGENT = "User-Agent";

    public static final String SIGNATURE_SALT = "XG?4VZ&4>9w"; // 盐值

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        builder.header(KEY_USER_AGENT, userAgent());

        String sessionId = null;
        String requestId = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis());
        String path = request.url() != null ? request.url().encodedPath() : "";

        if (sessionId == null) sessionId = "";
        String signature = signature(path, sessionId, requestId, timestamp);

        builder.header(KEY_SESSION_ID, sessionId);
        builder.header(KEY_REQUEST_ID, requestId);
        builder.header(KEY_TIMESTAMP, timestamp);
        builder.header(KEY_SIGNATURE, signature);

        Request compressedRequest = builder.build();
        Response response = chain.proceed(compressedRequest);
        Response.Builder responseBuilder = response.newBuilder();

        // 把当前session_id通过response带回去
        responseBuilder.header(KEY_SESSION_ID, sessionId);

        return responseBuilder.build();
    }

    private static final String signature(String path, String sessionId, String requestId, String
            timestamp) {
        return Hashing.sha256(String.format("%s&&%s&&%s&&%s&&%s",
                path,
                sessionId,
                requestId,
                timestamp,
                SIGNATURE_SALT));
    }

    public static final String userAgent() {

        StringBuffer sb = new StringBuffer();

        // 1. 应用名称
        sb.append("zjxw; ");

        // 2. 应用版本
        sb.append(getVersion()).append("; ");

        // 3. 设备uuid号
        sb.append(uniqueUUID()).append("; ");

        // 4. 设备信息
        try {
            sb.append(URLEncoder.encode(Build.MODEL, "utf-8")).append("; ");
        } catch (Exception e) {
            sb.append("; ");
        }

        // 5. 操作系统
        sb.append("Android; ");

        // 6. 系统版本
        try {
            sb.append(URLEncoder.encode(Build.VERSION.RELEASE, "utf-8")).append("; ");
        } catch (Exception e) {
            sb.append("; ");
        }

        // 7. 语言和地区
        sb.append(Locale.getDefault().getLanguage()).append("; ");

        // 8. 渠道信息
        try {
            sb.append(URLEncoder.encode("netTest", "utf-8"));
        } catch (Exception e) {
        }

        return sb.toString();
    }

    /**
     * 获取App版本名称
     *
     * @return
     */
    public static String getVersion() {
        try {
            Context ctx = ApiManager.getContext();
            return ctx.getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0.0";
    }

    public static String uniqueUUID() {
        String m_szDevIDShort = "24" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10)
                + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10)
                + (Build.PRODUCT.length() % 10);
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            serial = "serial"; // some value
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

}
