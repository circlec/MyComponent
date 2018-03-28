package zc.commonlib.router;


import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RouterCommonUtil {

    private static void toastInterruptInfo(final Activity activity, final Postcard postcard) {
        if (postcard.getTag() != null && postcard.getTag() instanceof String) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext((String) postcard.getTag());
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    if (!TextUtils.isEmpty(s) && activity != null) {
                        Toast.makeText(activity, (String) postcard.getTag(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void startMainActivity(final Activity activity) {
        ARouter.getInstance().build("/app/home").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }
        });
    }

    public static void startLoginActivity(final Activity activity) {
        ARouter.getInstance().build("/account/login").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }
        });
    }

    public static void startSettingActivity(final Activity activity) {
        ARouter.getInstance().build("/setting/setting").navigation(activity, new InterruptCallback() {
            @Override
            public void onInterrupt(Postcard postcard) {
                toastInterruptInfo(activity, postcard);
            }
        });
    }


}
