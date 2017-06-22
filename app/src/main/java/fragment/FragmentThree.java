package fragment;

import android.widget.TextView;

import com.pay.administrator.myapplication.R;

import base.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import mvp.BasePresenter;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/12 0012
 */

public class FragmentThree extends BaseFragment {
    @BindView(R.id.plus_one_button)
    TextView plusOneButton;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        plusOneButton.setText("我的资料");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_one;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @OnClick(R.id.plus_one_button)
    public void onViewClicked() {

    }
}
