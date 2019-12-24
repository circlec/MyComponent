package zc.setting.testfragment;

import zc.commonlib.base.IBasePresenter
import zc.commonlib.base.IBaseView

interface BlankContract {

    interface View : IBaseView {

    }

    interface Presenter : IBasePresenter<View> {
        fun login()

        fun saveNotifySwitchStatus(isOpen: Boolean)

    }
}