package zc.commonlib.base;


public interface IBaseView {

    int getLayoutId();

    void initView();

    void initData();

    void showLoading();

    void hideLoading();

    void showError();

    void showErrorMsg(String mErrorMsg);

    void showToast(String message);


}
