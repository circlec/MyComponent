package zc.account.login;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zc.account.data.bean.User;
import zc.account.data.source.AccountRepository;
import zc.commonlib.BasePresent;
import zc.commonlib.network.BaseResponse;

public class LoginPresenter extends BasePresent<LoginContract.View> implements LoginContract.Presenter {

    private final AccountRepository accountRepository;

    public LoginPresenter() {
        accountRepository = AccountRepository.getInstance();
    }

    @Override
    public void login(String userName, String password) {
        accountRepository.login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BaseResponse<User>>() {
                    @Override
                    public void accept(BaseResponse<User> userBaseResponse) throws Exception {
                        accountRepository.saveUserInfo(userBaseResponse.getValue());
                    }
                });
    }
}
