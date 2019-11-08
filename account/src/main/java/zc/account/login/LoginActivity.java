package zc.account.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.w3c.dom.Text;

import zc.account.R;
import zc.commonlib.BaseActivity;

@Route(path = "/account/login")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity_login);
        TextView tv = findViewById(R.id.tv_login);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login("15653125630","123456");
            }
        });
    }

    @Override
    public void showInputError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}

