package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pay.administrator.myapplication.R;

import java.util.List;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<String> title;
    private List<Fragment> views;
    private int[] imageResId;

    public CommonFragmentPagerAdapter(Context context, FragmentManager fm, List<String> title, List<Fragment> views, int[] imageResId) {
        super(fm);
        this.mContext = context;
        this.title = title;
        this.views = views;
        this.imageResId = imageResId;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }


    //配置标题的方法  TabLayout XML里面的文字属性配置只对这里生效
    @Override
    public CharSequence getPageTitle(int position) {
//        return title.get(position);

//        Drawable image = mContext.getResources().getDrawable(imageResId[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        // Replace blank spaces with image icon
//        SpannableString sb = new SpannableString("   " + title.get(position));
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return sb;
        return null;
    }

    //自定义的tab内容  可参考http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0731/3247.html
    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title.get(position));
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(imageResId[position]);
        return view;
    }
}
