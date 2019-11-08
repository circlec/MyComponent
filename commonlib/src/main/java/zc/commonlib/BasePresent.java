package zc.commonlib;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @作者 zhouchao
 * @日期 2019/11/8
 * @描述
 */
public class BasePresent<T extends IBaseView> implements IBasePresenter<T> {

    T mView;
    private CompositeDisposable compositeDisposable;

    @Override
    public void bindView(T view) {
        this.mView = view;
    }

    @Override
    public void unBindView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

}
