package zc.account.login;

import zc.account.data.bean.User;
import zc.account.data.source.AccountRepository;
import zc.commonlib.base.BasePresent;
import zc.commonlib.network.BaseObserver;
import zc.commonlib.network.RxUtils;

public class LoginPresenter extends BasePresent<LoginContract.View> implements LoginContract.Presenter {

    private final AccountRepository accountRepository;

    public LoginPresenter() {
        accountRepository = AccountRepository.getInstance();
    }

    @Override
    public void login(String userName, String password) {
        addSubscribe(accountRepository.login(userName, password)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<User>(mView) {

                                   @Override
                                   public void onNext(User user) {
                                       mView.showInputError();
                                       accountRepository.saveUserInfo(user);
                                   }
                               }
                ));

    }
}
