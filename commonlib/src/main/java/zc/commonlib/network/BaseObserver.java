package zc.commonlib.network;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;
import zc.commonlib.base.IBaseView;

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private IBaseView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseObserver(IBaseView view) {
        this.mView = view;
    }

    protected BaseObserver(IBaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(IBaseView view, boolean isShowError) {
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseObserver(IBaseView view, String errorMsg, boolean isShowError) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mView == null) {
            return;
        }
        mView.showError();
        if (!isShowError)
            return;
        if (!TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof HttpException) {
            mView.showErrorMsg("网络异常");
        } else if (e instanceof JsonSyntaxException) {
            mView.showErrorMsg("解析出错");
        } else if (e instanceof ServiceException) {
            if (!TextUtils.isEmpty(e.getMessage())) {
                mView.showErrorMsg(e.getMessage());
            } else {
                mView.showErrorMsg("网络异常");
            }
        } else {
            mView.showErrorMsg("网络异常");
        }
    }
}
