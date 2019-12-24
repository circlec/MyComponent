package zc.setting.testfragment

import zc.setting.R
import zc.commonlib.base.BaseFragment

open class BlankFragment : BaseFragment<BlankPresenter>(), BlankContract.View {

    override fun getLayoutId(): Int {
        return R.layout.setting_fragment_blank;
    }

    override fun initView() {
        mPresenter.login()
    }

    override fun initData() {

    }
}
