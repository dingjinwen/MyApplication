package fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pay.administrator.myapplication.MainActivity;
import com.pay.administrator.myapplication.R;

import base.BaseFragment;
import butterknife.BindView;
import customview.PagerSlidingHelper;
import customview.PagerSlidingIndicator;
import mvp.BasePresenter;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */

public class FragmentTwo extends BaseFragment implements ViewPager.OnPageChangeListener, PagerSlidingHelper.IPagerSliding {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.pager_indicator)
    PagerSlidingIndicator mIndicator;

    private static final String TAG_PERSONAL = "personal";
    private static final String TAG_VCARD = "vcard";
    protected int mFirstShow, mCurPosition; // 进页面的时候显示哪个tab

    private PagerSlidingHelper mSlidingHelper;

    private Fragment fragmentThree, fragmentFour;

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    protected void initView() {
        mainActivity.setTitle("页面二");
        mViewPager.addOnPageChangeListener(this);
        mSlidingHelper = new PagerSlidingHelper(mainActivity);
        mSlidingHelper.init(this);
    }

    @Override
    protected void initData() {

        fragmentThree = new FragmentThree();
        fragmentFour = new FragmentFour();

        mSlidingHelper.addFragment(TAG_PERSONAL, fragmentThree);
        mSlidingHelper.addFragment(TAG_VCARD, fragmentFour);
        mSlidingHelper.setup();

        // 默认显示电子名片
        mViewPager.setCurrentItem(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_two;
    }

    @Override
    protected BasePresenter loadPresenter() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public PagerSlidingIndicator getPagerSlidingIndicator() {
        return mIndicator;
    }

    @Override
    public String getTitle(String tag) {
        if (TAG_PERSONAL.equals(tag)) {
            return "个人资料";
        } else if (TAG_VCARD.equals(tag)) {
            return "电子名片";
        }
        return null;
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }


    @Override
    public PagerSlidingHelper.SlidingPagerAdapter getPagerAdapter() {
        return null;
    }
}
