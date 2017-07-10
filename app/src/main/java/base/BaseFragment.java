package base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import mvp.BasePresenter;
import mvp.IView;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {

    protected View view;
    protected P mPresenter;
    protected Unbinder unbinder;
    protected boolean isUseEventBus;

    public void setUseEventBus(boolean useEventBus) {
        isUseEventBus = useEventBus;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resLayoutId = getLayoutId();
        if (resLayoutId != 0) {
            view = inflater.inflate(resLayoutId, container, false);
            unbinder = ButterKnife.bind(this, view);
            initCommonData();
            initView();
            initData();
            if (isUseEventBus && !EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            return view;
        }
        return null;
    }

    private void initCommonData() {
        mPresenter = loadPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送消息,用于各个组件之间通信，接受消息页面存在时
     *
     * @param event 消息事件对象
     */
    public final <EVENT extends BaseEvent> void postEventMessage(@NonNull EVENT event) {
        // 发布事件
        EventBus.getDefault().post(event);
    }

    /**
     * 发送消息,用于各个组件之间通信,接受消息页面不存在时,postSticky发送事件, 那么可以不需要先注册, 也能接受到事件
     *
     * @param event 消息事件对象
     */
    public final <EVENT extends BaseEvent> void postStickyEventMessage(@NonNull EVENT event) {
        // 发布事件
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (isUseEventBus && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 6.0权限-回调结果处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract P loadPresenter();
}
