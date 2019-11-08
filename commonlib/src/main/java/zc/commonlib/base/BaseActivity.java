package zc.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.reflect.ParameterizedType;

import zc.commonlib.ActivityManager;

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    public final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpInit();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ActivityManager.add(this);
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
        super.onDestroy();
        ActivityManager.remove(this);
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
        Toast.makeText(this, mErrorMsg, Toast.LENGTH_SHORT).show();
        hideLoading();
    }
}
