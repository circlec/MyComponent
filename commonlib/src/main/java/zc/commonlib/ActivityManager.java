package zc.commonlib;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理，维护了activity集合
 * 可以跳转到特定activity,关闭所有actvity
 */
public class ActivityManager {
    private static List<Activity> activityList = new ArrayList<>();

    public static void add(Activity activity) {
        activityList.add(activity);
    }

    public static void remove(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAllActivities() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public static void finishNActivities(int count) {
        if (count > 0) {
            for (int i = activityList.size() - 1; i >= 0 && count > 0; i--) {
                activityList.get(i).finish();
                count--;
            }
        }
    }

    public static void finishTo(Class<? extends Activity> clazz) {
        for (int i = activityList.size() - 1; i >= 0; i--) {
            if (clazz != activityList.get(i).getClass()) {
                activityList.get(i).finish();
            }

        }
    }

    public static Activity getCurrActivity() {
        if (activityList.size() == 0) {
            return null;
        }
        return activityList.get(activityList.size() - 1);
    }

    public static Activity getActivityByName(String activityName) {
        if (activityList.size() == 0) {
            return null;
        }
        for(Activity activity:activityList){
            if (activityName.equals(activity.getClass().getSimpleName())){
                return activity;
            }
        }
        return null;
    }

    public void exitApp() {
        if (activityList != null) {
            synchronized (activityList) {
                for (Activity act : activityList) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
