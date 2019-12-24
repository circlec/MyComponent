package zc.setting.data.local;

import android.content.SharedPreferences;

import zc.setting.SettingApplication;
import static android.content.Context.MODE_PRIVATE;

/**
 * @作者 zhouchao
 * @日期 2019/3/28
 * @描述
 */
public class SpManager {
    private static SpManager spManager;
    private SharedPreferences spSetting;

    private SpManager() {
        spSetting = SettingApplication.getInstance().getSharedPreferences("setting", MODE_PRIVATE);
    }

    //2017年3月14日 17:09:32 修改为DCL 单例模式
    public static SpManager getInstance() {
        if (spManager == null) {
            synchronized (SpManager.class) {
                if (spManager == null)
                    spManager = new SpManager();
            }
        }
        return spManager;
    }

    public void saveNotifySwitchStatus(boolean isOpen) {
        spSetting.edit().putBoolean("isOpen", isOpen).apply();
    }

    public boolean getNotifySwitchStatus() {
        return spSetting.getBoolean("isOpen", false);
    }
}
