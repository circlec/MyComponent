package zc.commonlib.base;

import io.reactivex.disposables.Disposable;

public interface IBasePresenter<T extends IBaseView> {

    void bindView(T view);

    void unBindView();

    void addSubscribe(Disposable disposable);
}
