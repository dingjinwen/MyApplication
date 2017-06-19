package fragment;

import android.widget.TextView;

import com.example.administrator.myapplication.R;

import base.BaseFragment;
import butterknife.BindView;
import mvp.BasePresenter;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/12 0012
 */

public class FragmentFour extends BaseFragment {
    @BindView(R.id.plus_one_button)
    TextView plusOneButton;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        plusOneButton.setText("电子名片");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_one;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }
}
