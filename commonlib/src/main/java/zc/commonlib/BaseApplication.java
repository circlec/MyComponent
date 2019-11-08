package zc.commonlib;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @作者 zhouchao
 * @日期 2019/11/7
 * @描述
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRouter();
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initRouter() {
        if (BuildConfig.DEBUG) {
            //一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
