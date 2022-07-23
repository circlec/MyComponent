package zc.setting.test

import com.alibaba.android.arouter.facade.annotation.Route
import zc.commonlib.base.BaseActivity
import zc.commonlib.router.ARouterPath
import zc.setting.databinding.SettingActivityMyTestBinding

@Route(path = ARouterPath.SETTING_TEST_MYTEST)
open class MyTestActivity : BaseActivity<MyTestPresenter,SettingActivityMyTestBinding>(), MyTestContract.View {

    override fun initView() {
       showErrorMsg("MyTestActivity")
    }

    override fun initData() {

    }
}
