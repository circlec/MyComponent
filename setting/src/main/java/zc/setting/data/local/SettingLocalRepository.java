package zc.setting.data.local;

/**
 * @作者 zhouchao
 * @日期 2019/12/24
 * @描述
 */
public class SettingLocalRepository implements SettingLocalDataSource {
    private static SettingLocalRepository INSTANCE;
    private static SpManager spManager;

    public static SettingLocalRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingLocalRepository();
            spManager = SpManager.getInstance();
        }
        return INSTANCE;
    }

    @Override
    public void saveNotifySwitchStatus(boolean isOpen) {
        spManager.saveNotifySwitchStatus(isOpen);
    }

    @Override
    public boolean getNotifySwitchStatus() {
        return spManager.getNotifySwitchStatus();
    }
}
