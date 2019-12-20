package zc.setting.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;

import zc.commonlib.base.BaseActivity;
import zc.commonlib.router.ARouterPath;
import zc.setting.R;

@Route(path = ARouterPath.SETTING_SETTING_FRAGMENT)
public class MyFragmentActivity extends BaseActivity<MyFragmentPresenter> implements MyFragmentContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.setting_activity_my_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
