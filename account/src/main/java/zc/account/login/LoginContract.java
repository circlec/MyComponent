package zc.account.login;

import zc.commonlib.IBasePresenter;
import zc.commonlib.IBaseView;

public interface LoginContract {

    interface View extends IBaseView{

        void showInputError();

        void showProgress();

        void dismissProgress();

    }

    interface Presenter extends IBasePresenter<View> {


        void login(String userName, String password);

    }
}
