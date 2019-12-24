package zc.setting.testfragment

import zc.commonlib.base.BasePresent
import zc.commonlib.network.RetrofitManager
import zc.setting.data.SettingRepository

/**
 * Presenter
 */
open class BlankPresenter : BasePresent<BlankContract.View>(), BlankContract.Presenter {

    var repository: SettingRepository = TODO()

    fun BlankPresenter() {
        repository = SettingRepository.getInstance()
    }

    override fun login() {
        addSubscribe(repository
                .login("13121080560", "000000")
                .subscribe { mView.showErrorMsg(it.result.toString()) }
        )
    }

    override fun saveNotifySwitchStatus(isOpen: Boolean) {
        repository.saveNotifySwitchStatus(isOpen)
    }


}