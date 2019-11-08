package zc.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zc.commonlib.network.BaseResponse;
import zc.commonlib.network.RetrofitManager;
import zc.commonlib.router.ARouterPath;
import zc.commonlib.router.RouterCommonUtil;

@Route(path = ARouterPath.SETTING_SETTING)
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_setting);
        TextView textView = findViewById(R.id.tv);
        TextView tvToLogin = findViewById(R.id.tv_to_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitManager.getInstance().getRetrofitService(SettingService.class)
                        .login("15653125630", "123456")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BaseResponse<User>>() {
                            @Override
                            public void accept(BaseResponse<User> userBaseResponse) throws Exception {
                                Log.i("Test", "accept: " + userBaseResponse.getResult());
                            }
                        });
            }
        });
        tvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterCommonUtil.startLoginActivity(SettingsActivity.this);
                finish();
            }
        });
    }
}
