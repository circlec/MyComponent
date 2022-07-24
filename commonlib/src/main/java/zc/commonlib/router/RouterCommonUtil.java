package zc.commonlib.router;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterCommonUtil {

    public static void startLoginActivity(final Context context) {
        ARouter.getInstance().build(ARouterPath.ACCOUNT_LOGIN).navigation(context);
    }

    public static void startSettingActivity(final Context context) {
        ARouter.getInstance().build(ARouterPath.SETTING_SETTING).navigation(context);
    }

    public static void startMyTestActivity(final Context context) {
        ARouter.getInstance().build(ARouterPath.SETTING_TEST_MYTEST).navigation(context);
    }

    public static void startCameraPriview(final Context context) {
        ARouter.getInstance().build(ARouterPath.CAMERA_PREVIEW).navigation(context);
    }

    public static void startContentMain(final Context context) {
        ARouter.getInstance().build(ARouterPath.CONTENT_MAIN).navigation(context);
    }
}
