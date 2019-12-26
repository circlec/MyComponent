package zc.setting.test;

import zc.commonlib.base.IBasePresenter
import zc.commonlib.base.IBaseView

interface MyTestContract {

    interface View : IBaseView {

    }

    interface Presenter : IBasePresenter<View> {
        fun login()

    }
}