package zc.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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

        textView.setOnClickListener(this::login);
        tvToLogin.setOnClickListener(v -> RouterCommonUtil.startLoginActivity(SettingsActivity.this));
        tvToMytest.setOnClickListener(v -> RouterCommonUtil.startMyTestActivity(SettingsActivity.this));
    }

    @SuppressLint("CheckResult")
    private void login(View v) {
        SettingRepository.getInstance().login("13121080560", "000000")
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull User user) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(SettingsActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
