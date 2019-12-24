package zc.setting.data.remote;

import io.reactivex.Observable;
import zc.commonlib.network.BaseResponse;
import zc.commonlib.network.RetrofitManager;
import zc.setting.data.bean.User;

/**
 * @作者 zhouchao
 * @日期 2019/12/24
 * @描述
 */
public class SettingRemoteRepository implements SettingRemoteDataSource {

    private static SettingRemoteRepository INSTANCE;
    private static SettingRemoteDataSource settingRemoteDataSource;

    public static SettingRemoteRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingRemoteRepository();
            settingRemoteDataSource = RetrofitManager.getInstance().getRetrofitService(SettingRemoteDataSource.class);
        }
        return INSTANCE;
    }

    @Override
    public Observable<BaseResponse<User>> login(String userName, String password) {
        return settingRemoteDataSource.login(userName, password);
    }
}
