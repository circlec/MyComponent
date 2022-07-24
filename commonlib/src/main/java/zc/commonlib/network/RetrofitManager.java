package zc.commonlib.network;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zc.commonlib.BuildConfig;

/**
 * @作者 zhouchao
 * @日期 2019/11/7
 * @描述
 */
public class RetrofitManager {

    private HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private static final long DEFAULT_MILLISECONDS = 20000;

    private static final String BASE_URL = "https://record.rurrrr.com/api/record/v1/";

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
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new HeaderIntercept());
        OkHttpClient httpClient = builder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
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
