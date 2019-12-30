package zc.setting;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import zc.commonlib.network.BaseResponse;
import zc.commonlib.network.RxUtils;
import zc.commonlib.router.ARouterPath;
import zc.commonlib.router.RouterCommonUtil;
import zc.setting.data.SettingRepository;
import zc.setting.data.bean.User;

@Route(path = ARouterPath.SETTING_SETTING)
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_setting);
        TextView textView = findViewById(R.id.tv);
        TextView tvToLogin = findViewById(R.id.tv_to_login);
        TextView tvToMytest = findViewById(R.id.tv_to_mytest);

        textView.setOnClickListener(SettingsActivity::login);
        tvToLogin.setOnClickListener(v -> RouterCommonUtil.startLoginActivity(SettingsActivity.this));
        tvToMytest.setOnClickListener(v -> RouterCommonUtil.startMyTestActivity(SettingsActivity.this));
    }

    private static void login(View v) {
        Disposable disposable = SettingRepository.getInstance().login("15653125630", "123456")
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Consumer<BaseResponse<User>>() {
                    @Override
                    public void accept(BaseResponse<User> userBaseResponse) throws Exception {
                        Log.i("Test", "accept: " + userBaseResponse.getResult());
                    }
                });
    }

}
