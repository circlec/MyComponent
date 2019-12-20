package zc.setting.fragment;


import zc.commonlib.base.IBasePresenter;
import zc.commonlib.base.IBaseView;

public interface MyFragmentContract {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {

    }
}