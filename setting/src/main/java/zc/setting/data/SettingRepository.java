package zc.setting.data;

import io.reactivex.Observable;
import zc.commonlib.network.BaseResponse;
import zc.setting.data.bean.User;
import zc.setting.data.local.SettingLocalDataSource;
import zc.setting.data.local.SettingLocalRepository;
import zc.setting.data.remote.SettingRemoteDataSource;
import zc.setting.data.remote.SettingRemoteRepository;

/**
 * @作者 zhouchao
 * @日期 2019/12/24
 * @描述
 */
public class SettingRepository implements SettingLocalDataSource, SettingRemoteDataSource {

    private static SettingRepository INSTANCE;
    private static SettingRemoteDataSource settingRemoteDataSource;
    private static SettingLocalDataSource settingLocalDataSource;

    public static SettingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingRepository();
            settingRemoteDataSource = SettingRemoteRepository.getInstance();
            settingLocalDataSource = SettingLocalRepository.getInstance();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<BaseResponse<User>> login(String userName, String password) {
        return settingRemoteDataSource.login(userName, password);
    }

    @Override
    public void saveNotifySwitchStatus(boolean isOpen) {
        settingLocalDataSource.saveNotifySwitchStatus(isOpen);
    }

    @Override
    public boolean getNotifySwitchStatus() {
        return settingLocalDataSource.getNotifySwitchStatus();
    }

}
