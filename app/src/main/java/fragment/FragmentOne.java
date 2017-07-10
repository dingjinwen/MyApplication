package fragment;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.pay.administrator.myapplication.MainActivity;
import com.pay.administrator.myapplication.R;
import com.pay.base_adapter_list_library.BaseOneItemTypeAdapter;
import com.pay.base_adapter_list_library.ViewHolder;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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

    private static final int REQUECT_CODE_WRITE_CONTACTS = 4;

    private BaseOneItemTypeAdapter mAdapter;
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
        mAdapter = new BaseOneItemTypeAdapter(getActivity(), null, R.layout.goods_list_item_view) {
            @Override
            public void convert(int position, ViewHolder holder, Object item) {
                Goods goods = (Goods) mDatas.get(position);
                holder.setText(R.id.item_content, goods.getName());
                //测试6.0权限适配
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MPermissions.requestPermissions(FragmentOne.this, REQUECT_CODE_WRITE_CONTACTS , Manifest.permission.WRITE_CONTACTS);
                    }
                });
            }
        };
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
        List<Goods> mList=new ArrayList();
        mList.add(goods);
        mAdapter.updateData(mList);
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

    @PermissionGrant(REQUECT_CODE_WRITE_CONTACTS)
    public void requestContactSuccess()
    {
        Toast.makeText(getActivity(), "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_WRITE_CONTACTS)
    public void requestContactFailed()
    {
        Toast.makeText(getActivity(), "DENY ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }
}
