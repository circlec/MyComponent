package zc.commonlib.router;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterCommonUtil {

    public static void startLoginActivity(final Context context) {
        ARouter.getInstance().build("/account/login").navigation(context);
    }

    public static void startSettingActivity(final Context context) {
        ARouter.getInstance().build("/setting/setting").navigation(context);
    }


}
