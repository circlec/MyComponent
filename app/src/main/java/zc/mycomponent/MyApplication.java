package zc.mycomponent;


import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MyApplication extends Application {

    private Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ARouter.init(application);
    }
}
