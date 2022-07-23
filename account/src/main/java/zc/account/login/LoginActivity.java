package zc.account.login;

import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;

import zc.account.databinding.AccountActivityLoginBinding;
import zc.commonlib.base.BaseActivity;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.ACCOUNT_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter, AccountActivityLoginBinding> implements LoginContract.View {

    @Override
    public void initView() {
        binding.tvLogin.setOnClickListener(v -> mPresenter.login("15653125630", "123456"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void showInputError() {
        Log.i("Test", "showInputError: ");
    }

}

