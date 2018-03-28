package zc.account.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

import zc.account.R;
import zc.commonlib.BaseActivity;
import zc.commonlib.utils.FragmentUtil;

@Route(path = "/account/login")
public class LoginActivity extends BaseActivity {
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_frame);
        if(loginFragment==null){
            loginFragment = LoginFragment.newInstance();
            FragmentUtil.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.fl_content_frame);
        }

        LoginPresenter loginPresenter = new LoginPresenter(loginFragment);

    }
}

