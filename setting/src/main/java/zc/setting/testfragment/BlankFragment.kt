package zc.setting.testfragment

import zc.commonlib.base.BaseFragment
import zc.setting.databinding.SettingFragmentBlankBinding

open class BlankFragment : BaseFragment<BlankPresenter,SettingFragmentBlankBinding>(), BlankContract.View {

    override fun initView() {
        mPresenter.login()
    }

    override fun initData() {

    }
}
