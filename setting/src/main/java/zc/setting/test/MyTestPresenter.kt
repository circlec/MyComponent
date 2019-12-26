package zc.setting.test;

import io.reactivex.functions.Consumer
import zc.commonlib.base.BasePresent
import zc.commonlib.network.RxUtils
import zc.setting.data.SettingRepository

/**
 * Presenter
 */
open class MyTestPresenter : BasePresent<MyTestContract.View>(), MyTestContract.Presenter {

    override fun login() {
        addSubscribe(SettingRepository.getInstance().login("username", "password")
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribe(Consumer {
                    mView.showToast(it.userName)
                }))
    }


}