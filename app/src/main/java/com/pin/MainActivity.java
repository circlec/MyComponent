package com.pin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pin.databinding.AppActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import zc.commonlib.router.RouterCommonUtil;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.APP_HOME)
public class MainActivity extends AppCompatActivity {
    private AppActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AppActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                RouterCommonUtil.startLoginActivity(MainActivity.this);
                finish();
            }
        });
        binding.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                RouterCommonUtil.startSettingActivity(MainActivity.this);
                finish();
            }
        });

    }
}
