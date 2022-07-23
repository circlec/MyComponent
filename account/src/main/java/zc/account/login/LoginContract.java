package zc.account.login;

import zc.commonlib.base.IBasePresenter;
import zc.commonlib.base.IBaseView;

public interface LoginContract {

    interface View extends IBaseView{

        void showInputError();

    }

    interface Presenter extends IBasePresenter<View> {


        void login(String userName, String password);

    }
}
