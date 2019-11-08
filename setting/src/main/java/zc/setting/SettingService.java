package zc.setting;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zc.commonlib.network.BaseResponse;

/**
 * @作者 zhouchao
 * @日期 2019/11/7
 * @描述
 */
public interface SettingService {

    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<User>> login(@Field("userName") String userName, @Field("password") String password);

}
