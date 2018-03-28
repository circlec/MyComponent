package zc.commonlib.router;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;


abstract class InterruptCallback implements NavigationCallback {

    public void onFound(Postcard postcard){};

    public void onLost(Postcard postcard){};

    public void onArrival(Postcard postcard){};
}
