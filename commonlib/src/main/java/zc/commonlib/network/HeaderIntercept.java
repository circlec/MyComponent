package zc.commonlib.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * @作者 zhouchao
 * @日期 2019/8/23
 * @描述
 */
public class HeaderIntercept implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

        HttpUrl url = original.url();
        String oldPath = url.encodedPath();
        if (!oldPath.contains("oauth/token")) {
            addHeader(requestBuilder);
        }
        Request request = requestBuilder.build();
        return chain.proceed(request);

    }

    private void addHeader(Request.Builder requestBuilder) {
//        MMKV mmkv = MMKV.defaultMMKV();
//        if (!TextUtils.isEmpty(mmkv.decodeString(Constants.MMKV_KEY_ACCESS_TOKEN))) {
//            requestBuilder.addHeader("Authorization", "Basic cGhvbmU6cGhvbmVfc2VjcmV0");
//            requestBuilder.addHeader("Tenant-Id", mmkv.decodeString(Constants.MMKV_KEY_TENANT_ID));
//            requestBuilder.addHeader("Blade-Auth", mmkv.decodeString(Constants.MMKV_KEY_TOKEN_TYPE) + " " + mmkv.decodeString(Constants.MMKV_KEY_ACCESS_TOKEN));
//            if (BuildConfig.DEBUG) {
//                Log.i("okhttp", "Authorization: Basic cGhvbmU6cGhvbmVfc2VjcmV0");
//                Log.i("okhttp", "Tenant-Id: " + mmkv.decodeString(Constants.MMKV_KEY_TENANT_ID));
//                Log.i("okhttp", "Blade-Auth: " + mmkv.decodeString(Constants.MMKV_KEY_TOKEN_TYPE) + " " + mmkv.decodeString(Constants.MMKV_KEY_ACCESS_TOKEN));
//            }
//        } else {
//            if (BuildConfig.DEBUG) {
//                Log.i("okhttp", "TextUtils.isEmpty(mmkv.decodeString(Constants.MMKV_KEY_ACCESS_TOKEN)");
//            }
//        }

    }
}
