package com.pay.base_adapter_list_library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author dingjinwen
 * @description 适用多种ItemType的适配器
 * @date 2017/6/17 0017
 */

public abstract class BaseMultiItemTypeAdapter<T> extends BaseOneItemTypeAdapter<T> {
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public BaseMultiItemTypeAdapter(Context context, List<T> mDatas, MultiItemTypeSupport<T> mMultiItemTypeSupport) {
        super(context, mDatas, 0);
        this.mMultiItemTypeSupport = mMultiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public int getViewTypeCount() {
        return mMultiItemTypeSupport.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        int layoutId = mMultiItemTypeSupport.getLayoutId(itemType);
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(position, holder, getItem(position));
        return holder.getConvertView();
    }
}
