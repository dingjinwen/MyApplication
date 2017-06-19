package mvp;

import java.lang.ref.WeakReference;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */
public abstract class BasePresenter<V extends IView> implements IPresenter {
    private WeakReference actReference;

    @Override
    public void attachView(IView view) {
        actReference = new WeakReference(view);
    }

    @Override
    public void detachView() {
        if (actReference != null) {
            actReference.clear();
            actReference = null;
        }
    }

    @Override
    public V getView() {
        return (V) actReference.get();
    }
}
