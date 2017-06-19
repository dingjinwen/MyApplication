package com.example.base_adapter_list_library;

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
 * @description 有section和d多种item布局的Adapter
 * @date 2017/6/19 0019
 */

public abstract class BaseSectionMultiItemTypeAdapter<T> extends BaseMultiItemTypeAdapter<T> {

    private SectionSupport mSectionSupport;
    private static final int TYPE_SECTION = 300000;
    private LinkedHashMap<String, Integer> mSections;

    /**
     * @param context
     * @param mDatas               已经按数据规则排好序的
     * @param multiItemTypeSupport
     * @param mSectionSupport
     */
    public BaseSectionMultiItemTypeAdapter(Context context, List mDatas, MultiItemTypeSupport<T> multiItemTypeSupport, SectionSupport<T> mSectionSupport) {
        super(context, mDatas, null);
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

    private MultiItemTypeSupport<T> headerMultiItemTypeSupport = new MultiItemTypeSupport<T>() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_SECTION)
                return mSectionSupport.sectionHeaderLayoutId();
            else
                return mMultiItemTypeSupport.getLayoutId(itemType);
        }

        @Override
        public int getItemViewType(int position, T o) {
            return mSections.values().contains(position) ? TYPE_SECTION : mMultiItemTypeSupport.getItemViewType(position, o);
        }

        @Override
        public int getViewTypeCount() {
            return 1 + mMultiItemTypeSupport.getViewTypeCount();
        }
    };

    @Override
    public int getViewTypeCount() {
        return headerMultiItemTypeSupport.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return super.getCount() + mSections.size();
    }

    @Override
    public int getItemViewType(int position) {
        int realIndex = getIndexForPosition(position);
        return headerMultiItemTypeSupport.getItemViewType(position, mDatas.get(realIndex));
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
            int layoutId = headerMultiItemTypeSupport.getLayoutId(itemType);
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

