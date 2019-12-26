package zc.setting.test

import com.alibaba.android.arouter.facade.annotation.Route
import zc.setting.R
import zc.commonlib.base.BaseActivity
import zc.commonlib.router.ARouterPath

@Route(path = ARouterPath.SETTING_TEST_MYTEST)
open class MyTestActivity : BaseActivity<MyTestPresenter>(), MyTestContract.View {

    override fun getLayoutId(): Int {
        return R.layout.setting_activity_my_test;
    }

    override fun initView() {
       showErrorMsg("MyTestActivity")
    }

    override fun initData() {
        mPresenter.login()
    }
}
