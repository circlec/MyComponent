package zc.account.login;

import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import zc.account.R;
import zc.commonlib.base.BaseActivity;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.ACCOUNT_LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.account_activity_login;
    }

    @Override
    public void initView() {
        TextView tv = findViewById(R.id.tv_login);
        tv.setOnClickListener(v -> mPresenter.login("15653125630", "123456"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void showInputError() {
        Log.i("Test", "showInputError: ");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}

