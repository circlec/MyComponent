package zc.commonlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Class clazz = null;
        try {
            clazz = Class.forName(this.getClass().getName().replace("Activity", "Presenter"));
            mPresenter = (T) clazz.newInstance();
            mPresenter.bindView(this);
        } catch (ClassNotFoundException e) {
            Log.i("Test", "ClassNotFoundException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.i("Test", "IllegalAccessException");
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.i("Test", "InstantiationException");
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unBindView();
            mPresenter = null;
        }
        super.onDestroy();
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
