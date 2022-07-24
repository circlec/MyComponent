package com.pin.content;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.pin.content.databinding.ContentActivityContentMainBinding;

import zc.commonlib.base.BaseActivity;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.CONTENT_MAIN)
public class ContentMainActivity extends BaseActivity<ContentMainPresenter, ContentActivityContentMainBinding> implements ContentMainContract.View {

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

}