package com.zc.camera;

import zc.commonlib.base.IBasePresenter;
import zc.commonlib.base.IBaseView;

public interface CameraPriviewContract {

    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View> {


    }
}
