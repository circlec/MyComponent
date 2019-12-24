package zc.setting.testfragment

import zc.commonlib.base.BasePresent
import zc.commonlib.network.RetrofitManager
import zc.setting.SettingService

/**
 * Presenter
 */
open class BlankPresenter : BasePresent<BlankContract.View>(), BlankContract.Presenter {

    override fun login() {
        addSubscribe(RetrofitManager.getInstance()
                .getRetrofitService(SettingService::class.java)
                .login("13121080560", "000000")
                .subscribe { mView.showErrorMsg(it.result.toString()) }
        )
    }


}