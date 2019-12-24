package zc.setting.data.local;

/**
 * @作者 zhouchao
 * @日期 2019/12/24
 * @描述
 */
public interface SettingLocalDataSource {

    void saveNotifySwitchStatus(boolean isOpen);

    boolean getNotifySwitchStatus();

}
