package com.pay.administrator.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import adapter.CommonFragmentPagerAdapter;
import base.BaseActivity;
import base.BaseEvent;
import bean.Goods;
import butterknife.BindView;
import fragment.FragmentOne;
import fragment.FragmentTwo;
import presenter.GoodsPresenterImp;
import view.GoodsView;

public class MainActivity extends BaseActivity<GoodsPresenterImp> implements GoodsView {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    private List<String> mTitle = new ArrayList<>();
    private List<Fragment> mFragment = new ArrayList<>();
    private int[] imageResId = {R.drawable.selected,
            R.drawable.selected};

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTitle.add("tab1");
        mTitle.add("tab2");

        mFragment.add(new FragmentOne());
        mFragment.add(new FragmentTwo());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected GoodsPresenterImp loadPresenter() {
        return new GoodsPresenterImp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonFragmentPagerAdapter adapter = new CommonFragmentPagerAdapter(this, getSupportFragmentManager(), mTitle, mFragment, imageResId);
        viewPager.setAdapter(adapter);

        //为TabLayout设置ViewPager
        tabs.setupWithViewPager(viewPager);

        //设置tab内容
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        //请求网络
        if (mPresenter != null) {
            mPresenter.getGoods("");
        }
    }

    @Override
    public void getGoodsSuccess(Goods goods) {
        if (goods != null) {
            showToast(goods.getName() + "--------------" + goods.getPrice());

            //发送消息
            postStickyEventMessage(new BaseEvent());
        } else {
            showToast("-------获取结果为空-------");
        }

    }

    @Override
    public void getGoodsFail(int errorCode, String errorMessage) {
        showToast(errorMessage);
    }

}
