package com.zc.camera;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zc.camera.databinding.CameraActivityCameraPriviewBinding;

import zc.commonlib.base.BaseActivity;
import zc.commonlib.router.ARouterPath;

@Route(path = ARouterPath.CAMERA_PREVIEW)
public class CameraPriviewActivity extends BaseActivity<CameraPriviewPresenter, CameraActivityCameraPriviewBinding> implements CameraPriviewContract.View {

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

}