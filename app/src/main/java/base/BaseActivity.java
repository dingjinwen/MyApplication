package base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pay.administrator.myapplication.R;
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
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {
    protected P mPresenter;
    protected Unbinder unbinder;
    protected boolean isUseEventBus;
    protected Toolbar toolbar;
    protected TextView toolbar_title;

    public void setUseEventBus(boolean useEventBus) {
        isUseEventBus = useEventBus;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        View view = View.inflate(this, getLayoutId(), null);
        setContentView(view);
        unbinder = ButterKnife.bind(this);
        initCommonData();
        initView();
        initData();

        View v = findViewById(R.id.toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbar_title = (TextView) v.findViewById(R.id.toolbar_title);
            if (toolbar_title != null && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        if (isUseEventBus && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initCommonData() {
        mPresenter = loadPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbar_title != null && !TextUtils.isEmpty(title) && getSupportActionBar() != null) {
            toolbar_title.setText(title);
        }

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    //注意,和之前的方法一样,只是多了一个 sticky = true 的属性.
//    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
//    public void onEvent(MsgEvent event){
//    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (isUseEventBus && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 6.0权限-回调结果处理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract P loadPresenter();
}
