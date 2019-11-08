package zc.account.data.source;

import android.util.Log;

import com.google.gson.Gson;

import io.reactivex.Observable;
import zc.account.data.bean.User;
import zc.commonlib.network.BaseResponse;
import zc.commonlib.network.RetrofitManager;

public class AccountRepository implements AccountDataSource {
    private static AccountRepository INSTANCE = null;

    private AccountDataSource apiService;

    private AccountRepository() {
        apiService = RetrofitManager.getInstance().getRetrofitService(AccountDataSource.class);
    }

    public static AccountRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccountRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<BaseResponse<User>> login(String userName, String password) {
        return apiService.login(userName,password);
    }

    @Override
    public void saveUserInfo(User user) {
        Log.i("Test", "saveUserInfo: "+new Gson().toJson(user));
    }

}
