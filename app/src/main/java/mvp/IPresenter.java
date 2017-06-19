package mvp;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */

public interface IPresenter<V extends IView> {

    /**
     * @param view 绑定
     */
    void attachView(V view);

    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();

    /**
     * @return 获取View
     */
    IView getView();
}
