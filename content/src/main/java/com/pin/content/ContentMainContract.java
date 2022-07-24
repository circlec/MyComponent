package com.pin.content;

import zc.commonlib.base.IBasePresenter;
import zc.commonlib.base.IBaseView;

public interface ContentMainContract {

    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View> {


    }
}
