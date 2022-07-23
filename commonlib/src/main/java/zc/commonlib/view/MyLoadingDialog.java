package zc.commonlib.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xiasuhuei321.loadingdialog.manager.StyleManager;
import com.xiasuhuei321.loadingdialog.view.FinishDrawListener;
import com.xiasuhuei321.loadingdialog.view.LVCircularRing;
import com.xiasuhuei321.loadingdialog.view.LoadCircleView;
import com.xiasuhuei321.loadingdialog.view.RightDiaView;
import com.xiasuhuei321.loadingdialog.view.WrongDiaView;

import java.util.ArrayList;
import java.util.List;

import zc.commonlib.R;


/**
 * @作者 zhouchao
 * @日期 2021/5/13
 * @描述
 */
public class MyLoadingDialog implements FinishDrawListener {
    public final String TAG = "MyLoadingDialog";
    public static final int STYLE_RING = 0;
    public static final int STYLE_LINE = 1;
    private Context mContext;

    private LVCircularRing mLoadingView;
    private Dialog mLoadingDialog;
    private LinearLayout layout;
    private TextView loadingText;
    private RightDiaView mSuccessView;
    private WrongDiaView mFailedView;

    private String loadSuccessStr;
    private String loadFailedStr;
    private List<View> viewList;

    private boolean interceptBack = true;
    private boolean openSuccessAnim = true;
    private boolean openFailedAnim = true;
    private int speed = 1;
    private long time = 1000;
    private int loadStyle = STYLE_RING;

    private static StyleManager s = StyleManager.getDefault();
    private LoadCircleView mCircleLoadView;

    public enum Speed {
        SPEED_ONE,
        SPEED_TWO
    }

    private OnFinshListener o;
    private DismissListener d;

    public MyLoadingDialog(Context context) {
        mContext = context;
        // 首先得到整个View
        @SuppressWarnings("all")
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        initView(view);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.commonlib_loading_dialog) {
            @Override
            public void onBackPressed() {
                if (interceptBack) {
                    return;
                }
                MyLoadingDialog.this.close();
            }
        };
        // 设置返回键无效
        mLoadingDialog.setCancelable(!interceptBack);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mContext = null;
                if (d != null) d.dimiss();
            }
        });
        mLoadingDialog.getWindow().setDimAmount(0f);
        initStyle();
    }

    private void initView(View view) {
        layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        mLoadingView = (LVCircularRing) view.findViewById(R.id.lv_circularring);
        loadingText = (TextView) view.findViewById(R.id.loading_text);
        mSuccessView = (RightDiaView) view.findViewById(R.id.rdv_right);
        mFailedView = (WrongDiaView) view.findViewById(R.id.wv_wrong);
        mCircleLoadView = (LoadCircleView) view.findViewById(R.id.lcv_circleload);
        initData();
    }

    private void initData() {
        viewList = new ArrayList<>();
        viewList.add(mLoadingView);
        viewList.add(mSuccessView);
        viewList.add(mFailedView);
        viewList.add(mCircleLoadView);

        mSuccessView.setOnDrawFinishListener(this);
        mFailedView.setOnDrawFinishListener(this);
    }

    @Override
    public void dispatchFinishEvent(View v) {
        if (v instanceof WrongDiaView) {
            h.sendEmptyMessageDelayed(2, time);
        } else {
            h.sendEmptyMessageDelayed(1, time);
        }
    }

    private void hideAll() {
        for (View v : viewList) {
            if (v.getVisibility() != View.GONE) {
                v.setVisibility(View.GONE);
            }
        }
    }

    private void setParams(int size) {
        if (size < 0) return;
        ViewGroup.LayoutParams successParams = mSuccessView.getLayoutParams();
        successParams.height = size;
        successParams.width = size;
        mSuccessView.setLayoutParams(successParams);

        ViewGroup.LayoutParams failedParams = mFailedView.getLayoutParams();
        failedParams.height = size;
        failedParams.width = size;
        mFailedView.setLayoutParams(failedParams);

        ViewGroup.LayoutParams loadingParams = mLoadingView.getLayoutParams();
        loadingParams.height = size;
        loadingParams.width = size;
    }

    // 会在最后将所有消息移除
    @SuppressLint("HandlerLeak")
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyLoadingDialog.this.close();
            if (o != null) o.onFinish();
        }
    };


    private void initStyle() {
        if (s != null) {
            setInterceptBack(s.isInterceptBack());
            setParams(s.getContentSize());
            setTextSize(s.getTextSize());
            setShowTime(s.getShowTime());
            if (!s.isOpenAnim()) {
                closeFailedAnim();
                closeSuccessAnim();
            }
            setLoadingText(s.getLoadText());
            setSuccessText(s.getSuccessText());
            setFailedText(s.getFailedText());
            setLoadStyle(s.getLoadStyle());
        }
    }

    //----------------------------------对外提供的api------------------------------//

    /**
     * please invoke show() method at last,because it's
     * return value is void
     * 请在最后调用show，因此show返回值为void会使链式api断开
     */
    public void show() {
        hideAll();
        if (loadStyle == STYLE_RING) {
            mLoadingView.setVisibility(View.VISIBLE);
            mCircleLoadView.setVisibility(View.GONE);
            mLoadingDialog.show();
            mLoadingView.startAnim();
            Log.i("show", "style_ring");
        } else if (loadStyle == STYLE_LINE) {
            mCircleLoadView.setVisibility(View.VISIBLE);
            mLoadingView.setVisibility(View.GONE);
            mLoadingDialog.show();
            Log.i("show", "style_line");
        }
    }

    /**
     * set load style
     * 设置load的样式，目前支持转圈圈和菊花转圈圈
     *
     * @param style
     */
    public MyLoadingDialog setLoadStyle(int style) {
        if (style >= 3) {
            throw new IllegalArgumentException("Your style is wrong: style = " + style);
        }
        this.loadStyle = style;
        return this;
    }

    /**
     * 让这个dialog消失，在拦截back事件的情况下一定要调用这个方法！
     * 在调用了该方法之后如需再次使用loadingDialog，请新创建一个
     * LoadingDialog对象。
     */
    public void close() {
        h.removeCallbacksAndMessages(null);
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim();
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 设置加载时的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    public MyLoadingDialog setLoadingText(String msg) {
        if (msg != null) {
            loadingText.setVisibility(View.VISIBLE);
            loadingText.setText(msg);
        } else loadingText.setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置加载成功的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    public MyLoadingDialog setSuccessText(String msg) {
        loadSuccessStr = msg;
        return this;
    }

    /**
     * 设置加载失败的文字提示
     *
     * @param msg 文字
     * @return 这个对象
     */
    public MyLoadingDialog setFailedText(String msg) {
        loadFailedStr = msg;
        return this;
    }

    /**
     * 关闭动态绘制
     */
    public MyLoadingDialog closeSuccessAnim() {
        this.openSuccessAnim = false;
        return this;
    }

    /**
     * 关闭动态绘制
     */
    public MyLoadingDialog closeFailedAnim() {
        this.openFailedAnim = false;
        return this;
    }

    /**
     * 设置是否拦截back，默认会拦截
     *
     * @param interceptBack true拦截back，false不拦截
     * @return 这个对象
     */
    public MyLoadingDialog setInterceptBack(boolean interceptBack) {
        this.interceptBack = interceptBack;
        mLoadingDialog.setCancelable(!interceptBack);
        return this;
    }

    /**
     * 当前dialog是否拦截back事件
     *
     * @return 如果拦截返回true，反之false
     */
    public boolean getInterceptBack() {
        return interceptBack;
    }


    /**
     * 返回当前绘制的速度
     *
     * @return 速度
     */
    public int getSpeed() {
        return this.speed;
    }


    /**
     * 设置中间弹框的尺寸
     *
     * @param size 尺寸，单位px
     * @return 这个对象
     */
    public MyLoadingDialog setSize(int size) {
//        int dip = SizeUtils.px2dip(mContext, size);
        if (size <= 50) return this;
        setParams(size);
        return this;
    }

    /**
     * 设置反馈展示时间
     *
     * @param time 时间
     * @return 这个对象
     */
    public MyLoadingDialog setShowTime(long time) {
        if (time < 0) return this;
        this.time = time;
        return this;
    }

    /**
     * set the size of load text size
     * 设置加载字体大小
     *
     * @param size 尺寸，单位sp，来将sp转换为对应的px值
     * @return 这个对象
     */
    public MyLoadingDialog setTextSize(float size) {
        if (size < 0) return this;
        loadingText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    public static void initStyle(StyleManager style) {
        if (style != null)
            s = style;
    }

    /**
     * dispatch draw finish event
     * 传递绘制完成的事件
     *
     * @param o 回调接口
     */
    public void setOnFinishListener(OnFinshListener o) {
        this.o = o;
    }

    /**
     * 设置 dismiss 监听
     *
     * @param d dismiss callback
     */
    public MyLoadingDialog setDimissListener(DismissListener d) {
        this.d = d;
        return this;
    }

    /**
     * 监听器
     */
    public interface OnFinshListener {
        void onFinish();
    }

    public interface DismissListener {
        void dimiss();
    }

}
