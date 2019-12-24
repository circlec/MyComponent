package zc.setting.data.remote;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zc.commonlib.network.BaseResponse;
import zc.setting.data.bean.User;

/**
 * @作者 zhouchao
 * @日期 2019/12/24
 * @描述
 */
public interface SettingRemoteDataSource {

    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<User>> login(@Field("userName") String userName, @Field("password") String password);

}
