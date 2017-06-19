package com.example.base_adapter_recyclerview_library;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public abstract class BaseMultiItemTypeAdapter<T> extends BaseOneItemTypeAdapter<T> {
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public BaseMultiItemTypeAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        return holder;
    }

}
