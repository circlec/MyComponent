package zc.commonlib;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @作者 zhouchao
 * @日期 2019/11/8
 * @描述
 */
public class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {

    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Class clazz = null;
        try {
            clazz = Class.forName(this.getClass().getName().replace("Fragment", "Presenter"));
            mPresenter = (T) clazz.newInstance();
            mPresenter.bindView(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showLoading() {
        // TODO: 2019/11/8
    }

    @Override
    public void hideLoading() {
        // TODO: 2019/11/8
    }
}
