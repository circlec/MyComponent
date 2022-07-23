package zc.commonlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import zc.commonlib.view.MyLoadingDialog;

/**
 * @作者 zhouchao
 * @日期 2019/11/8
 * @描述
 */
public abstract class BaseFragment<T extends IBasePresenter, V extends ViewBinding> extends Fragment implements IBaseView {
    public final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;
    protected V binding;
    protected MyLoadingDialog loadingDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<V> clazz = (Class<V>) pt.getActualTypeArguments()[1];
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (V) method.invoke(null, getLayoutInflater());
            return Objects.requireNonNull(binding).getRoot();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpInit();
        initView();
        initData();
    }

    private void mvpInit() {
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
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.unBindView();
            mPresenter = null;
        }
        binding = null;
        super.onDestroyView();

    }

    @Override
    public void showLoading() {
        if(loadingDialog==null){
            loadingDialog = new MyLoadingDialog(getContext());
            loadingDialog.setLoadingText("加载中")
                    .show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.close();
            loadingDialog = null;
        }
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
    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }
}
