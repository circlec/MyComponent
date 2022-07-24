package com.pin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pin.databinding.AppActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import zc.commonlib.router.RouterCommonUtil;
import zc.commonlib.router.ARouterPath;
import zc.commonlib.utils.statusbar.StatusBarUtil;

@Route(path = ARouterPath.APP_HOME)
public class MainActivity extends AppCompatActivity {

    private AppActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = AppActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        StatusBarUtil.setStatusColor(this.getWindow(), getResources().getColor(R.color.commonlib_status_bar), 1f);
        binding.tvAccount.setOnClickListener(v -> {
            RouterCommonUtil.startLoginActivity(MainActivity.this);
//            finish();
        });
        binding.tvSetting.setOnClickListener(v -> {
            RouterCommonUtil.startSettingActivity(MainActivity.this);
//            finish();
        });
        binding.tvCamera.setOnClickListener(v->{
            RouterCommonUtil.startCameraPriview(MainActivity.this);
//            finish();
        });
        binding.tvContent.setOnClickListener(v->{
            RouterCommonUtil.startContentMain(MainActivity.this);
//            finish();
        });

    }
}
