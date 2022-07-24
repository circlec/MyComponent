package zc.commonlib.base;

import static android.view.View.OVER_SCROLL_NEVER;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import zc.commonlib.ActivityManager;
import zc.commonlib.R;
import zc.commonlib.router.RouterCommonUtil;

/**
 * @作者 zhouchao
 * @日期 2019/11/8
 * @描述
 */
public abstract class BaseDialogFragment<T extends IBasePresenter, V extends ViewBinding> extends DialogFragment implements IBaseView {
    public final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;
    protected V binding;

    protected LoadingDialog loadingDialog;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.commonlib_not_fullscreen_dialog);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpInit();
        initView();
        initData();
    }

    public void mvpInit() {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            mPresenter = clazz.newInstance();
            mPresenter.bindView(this);
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
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
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
    }


    @Override
    public void showLoading() {
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.setLoadingText("加载中")
                .show();
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.close();
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    public RecyclerView initRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        return recyclerView;
    }

}
