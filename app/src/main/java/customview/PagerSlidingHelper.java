package customview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制一个页面显示一个或多个tab
 * Created by luo_shuai on 2016/6/16 0016.
 */
public class PagerSlidingHelper {

    private AppCompatActivity mActivity;
    private List<String> mFragmentTags;
    private SparseArray<Fragment> mFragments;

    private IPagerSliding mPagerSliding;

    private SlidingPagerAdapter mAdapter;

    public PagerSlidingHelper(AppCompatActivity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity can't be null.");
        }
        mActivity = activity;
        mFragmentTags = new ArrayList<>(3);
        mFragments = new SparseArray<>(3);
    }

    public void init(IPagerSliding sliding) {
        if (sliding == null) {
            throw new IllegalArgumentException("IPagerSliding can't be null.");
        }
        mPagerSliding = sliding;

        mActivity.setTitle("");

        PagerSlidingIndicator indicator = mPagerSliding.getPagerSlidingIndicator();
        if (indicator != null) {
            indicator.setVisibility(View.GONE);
        }
    }

    public void addFragment(String tag, Fragment fragment) {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("tag can't be empty.");
        }
        if (mFragmentTags.contains(tag)) {
            throw new IllegalArgumentException("tag=" + tag + " has been added.");
        }
        if (fragment == null) {
            throw new IllegalArgumentException("fragment can't be null.");
        }
        mFragmentTags.add(tag);
        mFragments.put(tag.hashCode(), fragment);
    }

    public void setup() {
        if (mFragmentTags.size() == 0) {
            return;
        }

        PagerSlidingIndicator indicator = mPagerSliding.getPagerSlidingIndicator();
        ActionBar actionBar = mActivity.getSupportActionBar();

        // 只有1个页面
        if (mFragmentTags.size() == 1) {
            if (indicator != null) {
                indicator.setVisibility(View.GONE);
            }
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }
            String title = mPagerSliding.getTitle(mFragmentTags.get(0));
            mActivity.setTitle(title);
        } else {
            // 多个页面
            if (indicator != null) {
                indicator.setVisibility(View.VISIBLE);
            }
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }
            mActivity.setTitle("");
        }

        ViewPager viewPager = mPagerSliding.getViewPager();
        if (viewPager == null) {
            throw new IllegalArgumentException("viewPager can't be null.");
        }

        SlidingPagerAdapter adapter = getAdapter();

        // 添加Fragment
        for (String tag : mFragmentTags) {
            adapter.add(mFragments.get(tag.hashCode()));
        }

        viewPager.setAdapter(adapter);

        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }

    public SlidingPagerAdapter getAdapter() {
        if (mAdapter != null) {
            return mAdapter;
        }
        SlidingPagerAdapter adapter = mPagerSliding.getPagerAdapter();

        if (adapter == null) {
            adapter = new SlidingPagerAdapter(mActivity.getSupportFragmentManager());
        }

        mAdapter = adapter;
        return adapter;
    }

    public String getCurrentTag() {
        ViewPager viewPager = mPagerSliding.getViewPager();
        int position = viewPager.getCurrentItem();
        if (position >= 0 && position < mFragmentTags.size()) {
            return mFragmentTags.get(position);
        }
        return null;
    }

    public int getPositionByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mFragmentTags.size(); i++) {
            if (TextUtils.equals(tag, mFragmentTags.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return mFragmentTags.size();
    }

    public interface IPagerSliding {
        PagerSlidingIndicator getPagerSlidingIndicator();

        String getTitle(String tag);

        ViewPager getViewPager();

        SlidingPagerAdapter getPagerAdapter();

    }

    public class SlidingPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public SlidingPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        public void add(Fragment fragment) {
            fragments.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
