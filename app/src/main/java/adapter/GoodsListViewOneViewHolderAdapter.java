package adapter;

import android.content.Context;

import com.pay.administrator.myapplication.R;

import java.util.List;

import base.BaseOneViewHolderAdapter;
import base.BaseViewHolder;
import bean.Goods;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/8 0008
 */

public class GoodsListViewOneViewHolderAdapter extends BaseOneViewHolderAdapter<Goods> {

    private Context mContext;
    private List<Goods> mDatas;
    private int itemLayoutId;

    /**
     * 初始化通用Adapter
     *
     * @param context      上下文
     * @param datas        需要显示的数据集合
     * @param itemLayoutId 子布局文件
     */
    public GoodsListViewOneViewHolderAdapter(Context context, List<Goods> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
        mContext = context;
        mDatas = datas;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public void convert(BaseViewHolder helper, Goods item, int position) {
        helper.setTextForTextView(R.id.item_content, item.getName());
    }
}
