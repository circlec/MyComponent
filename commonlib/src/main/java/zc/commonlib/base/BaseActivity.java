package zc.commonlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import zc.commonlib.ActivityManager;
import zc.commonlib.view.MyLoadingDialog;

public abstract class BaseActivity<T extends IBasePresenter, V extends ViewBinding> extends AppCompatActivity implements IBaseView {
    public final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;
    protected V binding;
    protected MyLoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpInit();
        super.onCreate(savedInstanceState);
        initBinding();
        ActivityManager.add(this);
        initView();
        initData();
    }
    private void initBinding() {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<V> clazz = (Class<V>) pt.getActualTypeArguments()[1];
            Method method = clazz.getMethod("inflate", LayoutInflater.class);
            binding = (V) method.invoke(null, getLayoutInflater());
            setContentView(Objects.requireNonNull(binding).getRoot());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private void mvpInit() {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();
            mPresenter.bindView(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("mPresenter init error");
        } catch (InstantiationException e) {
            throw new RuntimeException("mPresenter init error");
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unBindView();
            mPresenter = null;
        }
        binding = null;
        super.onDestroy();
        ActivityManager.remove(this);
    }

    @Override
    public void showLoading() {
        if(loadingDialog==null){
            loadingDialog = new MyLoadingDialog(this);
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
        Toast.makeText(this, mErrorMsg, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
