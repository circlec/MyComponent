package zc.commonlib.base;


public interface IBaseView {

    void showToast(String message);

    void showToast(int resId);

    void initView();

    void initData();

    void showLoading();

    void hideLoading();

    void showError();

    void showErrorMsg(String mErrorMsg);


}
