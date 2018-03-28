package zc.account.login;


import zc.account.data.source.AccountRepository;
import zc.account.data.source.local.AccountLocalDataSource;
import zc.account.data.source.remote.AccountRemoteDataSource;

public class LoginPresenter implements LoginContract.Presenter {

    private final AccountRepository accountRepository;
    private final LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        accountRepository = AccountRepository.getInstance(AccountRemoteDataSource.getInstance(), AccountLocalDataSource.getInstance());
        this.loginView = loginView;
        loginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestory() {

    }

    @Override
    public void login(String userName, String password) {

    }
}
