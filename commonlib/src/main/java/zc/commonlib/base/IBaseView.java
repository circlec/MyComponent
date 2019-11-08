package zc.commonlib.base;


public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showError();

    void showErrorMsg(String mErrorMsg);
}
