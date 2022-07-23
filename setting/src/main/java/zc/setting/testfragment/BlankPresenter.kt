package zc.setting.testfragment

import zc.commonlib.base.BasePresent
import zc.commonlib.network.BaseObserver
import zc.commonlib.network.RxUtils
import zc.setting.data.SettingRepository
import zc.setting.data.bean.User

/**
 * Presenter
 */
open class BlankPresenter : BasePresent<BlankContract.View>(), BlankContract.Presenter {

     var repository: SettingRepository = SettingRepository.getInstance()

    fun BlankPresenter() {
    }

    override fun login() {
        addSubscribe(repository.login("13121080560", "000000")
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(object : BaseObserver<User?>(mView) {
                    override fun onNext(user: User) {
                        mView.showErrorMsg("ddd")
                    }
                }
                ))
    }

    override fun saveNotifySwitchStatus(isOpen: Boolean) {
        repository.saveNotifySwitchStatus(isOpen)
    }


}