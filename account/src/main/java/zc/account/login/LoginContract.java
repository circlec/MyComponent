package zc.account.login;

import zc.commonlib.BasePresenter;
import zc.commonlib.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showInputError();

        void showProgress();

        void dismissProgress();

    }

    interface Presenter extends BasePresenter {


        void login(String userName, String password);

    }
}
