package zc.commonlib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.ParameterizedType;

/**
 * @作者 zhouchao
 * @日期 2019/11/8
 * @描述
 */
public class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    public final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();
            mPresenter.bindView(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("mPresenter init error");
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException("mPresenter init error");
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

    @Override
    public void showError() {
        hideLoading();
    }

    @Override
    public void showErrorMsg(String mErrorMsg) {
        Toast.makeText(getContext(), mErrorMsg, Toast.LENGTH_SHORT).show();
        hideLoading();
    }
}
