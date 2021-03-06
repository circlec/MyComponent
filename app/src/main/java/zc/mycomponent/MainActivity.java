package zc.mycomponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import zc.commonlib.router.RouterCommonUtil;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.APP_HOME)
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);
        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                RouterCommonUtil.startLoginActivity(MainActivity.this);
                finish();
            }
        });
        TextView tv_setting = findViewById(R.id.tv_setting);
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                RouterCommonUtil.startSettingActivity(MainActivity.this);
                finish();
            }
        });

    }
}
