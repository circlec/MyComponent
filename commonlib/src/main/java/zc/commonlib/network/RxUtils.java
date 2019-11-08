package zc.commonlib.network;


import android.content.Context;
import android.net.ConnectivityManager;

import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<T, T> rxFlSchedulerHelper() {
        return flowable -> flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap((Function<BaseResponse<T>, Observable<T>>) baseResponse -> {
                    if (baseResponse.getResult() == BaseResponse.SUCCESS
                            && baseResponse.getValue() != null) {
                        return createData(baseResponse.getValue());
                    } else {
                        return Observable.error(new ServiceException(baseResponse.getMessage(), baseResponse.getResult()));
                    }
                });
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<Response<BaseResponse<T>>, T> handleResponseResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap((Function<Response<BaseResponse<T>>, Observable<T>>) baseResponse -> {
                    BaseResponse response = baseResponse.body();
                    int responseCode = baseResponse.code();
                    if (responseCode == 500) {
                        return Observable.error(new HttpException(baseResponse));
                    } else {
                        if (response.getResult() == BaseResponse.SUCCESS
                                && response.getValue() != null) {
                            return createData((T) response.getValue());
                        } else {
                            return Observable.error(new ServiceException(response.getMessage(), response.getResult()));
                        }
                    }
                });
    }

    /**
     * 得到 Observable
     *
     * @param <T> 指定的泛型类型
     * @return Observable
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public static ObservableTransformer<BaseResponse, BaseResponse> handleBaseResponseResult() {
        return httpResponseObservable ->
                httpResponseObservable.flatMap((Function<BaseResponse, Observable<BaseResponse>>) baseResponse -> {
                    if (baseResponse != null && baseResponse.getResult() == BaseResponse.SUCCESS) {
                        return createData(baseResponse);
                    } else {
                        return Observable.error(new ServiceException(baseResponse.getMessage(), baseResponse.getResult()));
                    }
                });
    }

    /**
     * 检查是否有可用网络
     */
    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
