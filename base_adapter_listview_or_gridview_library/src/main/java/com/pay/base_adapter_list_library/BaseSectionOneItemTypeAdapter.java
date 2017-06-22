package com.pay.base_adapter_list_library;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dingjinwen
 * @description 只有section和一种item布局的Adapter
 * @date 2017/6/17 0017
 */

public abstract class BaseSectionOneItemTypeAdapter<T> extends BaseMultiItemTypeAdapter<T> {

    private SectionSupport mSectionSupport;
    private static final int TYPE_SECTION = 0;
    private static final int TYPE_COMMON_ITEM = 1;
    private LinkedHashMap<String, Integer> mSections;

    /**
     * @param context
     * @param mDatas          已经按数据规则排好序的
     * @param itemLayoutId
     * @param mSectionSupport
     */
    public BaseSectionOneItemTypeAdapter(Context context, List mDatas, int itemLayoutId, SectionSupport<T> mSectionSupport) {
        super(context, mDatas, null);
        mItemLayoutId = itemLayoutId;
        mMultiItemTypeSupport = multiItemTypeSupport;
        this.mSectionSupport = mSectionSupport;
        mSections = new LinkedHashMap<>();
        findSections();
    }

    protected void updateData(List<T> data) {
        if (data != null) {
            mDatas.clear();
            mDatas.addAll(data);
            findSections();
            notifyDataSetChanged();
        }
    }

    private MultiItemTypeSupport<T> multiItemTypeSupport = new MultiItemTypeSupport<T>() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_SECTION)
                return mSectionSupport.sectionHeaderLayoutId();
            else
                return mItemLayoutId;
        }

        @Override
        public int getItemViewType(int position, T o) {
            return mSections.values().contains(position) ? TYPE_SECTION : TYPE_COMMON_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    };

    @Override
    public int getViewTypeCount() {
        return mMultiItemTypeSupport.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return super.getCount() + mSections.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        int realIndex = getIndexForPosition(position);
        if (itemType == TYPE_SECTION) {
            ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, mSectionSupport.sectionHeaderLayoutId(), realIndex);
            viewHolder.setText(mSectionSupport.sectionTitleTextViewId(), mSectionSupport.getTitle(mDatas.get(realIndex)));
            return viewHolder.getConvertView();
        } else {
            int layoutId = mItemLayoutId;
            ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, realIndex);
            convert(position, holder, getItem(realIndex));
            return holder.getConvertView();
        }
    }

    /**
     * 根据数据规则，获取section数据
     */
    public void findSections() {
        int n = mDatas.size();
        int nSections = 0;
        mSections.clear();

        for (int i = 0; i < n; i++) {
            String sectionName = mSectionSupport.getTitle(mDatas.get(i));

            if (!mSections.containsKey(sectionName)) {
                mSections.put(sectionName, i + nSections);
                nSections++;
            }
        }

        Set<Map.Entry<String, Integer>> entrySet = mSections.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            Log.e("djw", "key:" + entry.getKey());
            Log.e("djw", "value:" + entry.getValue());
        }
    }

    /**
     * 获取position在数据源（mDatas）里面的实际下标
     *
     * @param position
     * @return
     */
    public int getIndexForPosition(int position) {
        int nSections = 0;

        Set<Map.Entry<String, Integer>> entrySet = mSections.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() < position) {
                nSections++;
            }
        }
        Log.d("djw", "position----" + position + "---index---" + (position - nSections));
        return position - nSections;
    }

}
