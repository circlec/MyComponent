package zc.commonlib.network;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者 zhouchao
 * @日期 2019/11/7
 * @描述
 */
public class RetrofitManager {

    private HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private static final long DEFAULT_MILLISECONDS = 20000;

    private static final String BASE_URL = "http://genius.enn.cn/encdata-wzd-safe/";

    private RetrofitManager() {
    }

    private static class RetrofitManagerManagerInstance {
        private static final RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerManagerInstance.RETROFIT_MANAGER;
    }

    /**
     * 获取retrofit的实例
     *
     * @return Retrofit
     */
    private <T> T getService(Class<T> cls) {
        Retrofit retrofit;
        if (retrofitHashMap.containsKey(cls.getSimpleName())) {
            retrofit = retrofitHashMap.get(cls.getSimpleName());
        } else {
            retrofit = createrRetrofit();
            retrofitHashMap.put(cls.getSimpleName(), retrofit);
        }
        return retrofit.create(cls);
    }

    /**
     * 创建retrofit
     *
     * @return Retrofit
     */
    private Retrofit createrRetrofit() {

        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }


    /**
     * 根据各模块业务接口 获取不同的retrofit service接口对象
     */
    public <T> T getRetrofitService(Class<T> cls) {
        return getService(cls);
    }
}
