package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.pay.administrator.myapplication.MainActivity;
import com.pay.administrator.myapplication.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import adapter.GoodsListViewOneViewHolderAdapter;
import base.BaseEvent;
import base.BaseFragment;
import bean.Goods;
import butterknife.BindView;
import presenter.GoodsPresenterImp;
import view.GoodsView;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */
public class FragmentOne extends BaseFragment<GoodsPresenterImp> implements GoodsView {


    @BindView(R.id.listView)
    ListView listView;

    private GoodsListViewOneViewHolderAdapter mAdapter;

    private List<Goods> mDatas = new ArrayList<>();

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        setUseEventBus(true);
        mainActivity.setTitle("页面一");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_one;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new GoodsListViewOneViewHolderAdapter(getActivity(), mDatas, R.layout.goods_list_item_view);
        listView.setAdapter(mAdapter);
        //请求网络
        if (mPresenter != null) {
            mPresenter.getGoods("");
        }
    }

    @Override
    protected GoodsPresenterImp loadPresenter() {
        return new GoodsPresenterImp();
    }

    @Override
    public void getGoodsSuccess(Goods goods) {
        mDatas.add(goods);
        mAdapter.refresh(mDatas);
    }

    @Override
    public void getGoodsFail(int errorCode, String errorMessage) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {
        Log.d("djw", "111111111111111111");
        showToast("接受到post消息了");
    }

    //注意,和之前的方法一样,只是多了一个 sticky = true 的属性.
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(BaseEvent event) {
        Log.d("djw", "2222222222222");
        showToast("接受到post消息了");
    }
}
