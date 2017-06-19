package com.example.base_adapter_recyclerview_library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public abstract class BaseSectionOneItemTypeAdapter<T> extends BaseMultiItemTypeAdapter<T> {
    private SectionSupport mSectionSupport;
    private static final int TYPE_SECTION = 0;
    private static final int TYPE_COMMON_ITEM = 1;
    private LinkedHashMap<String, Integer> mSections;

    /**
     * @param context
     * @param layoutId
     * @param datas          已经按数据规则排好序的
     * @param sectionSupport
     */
    public BaseSectionOneItemTypeAdapter(Context context, int layoutId, List<T> datas, SectionSupport sectionSupport) {
        super(context, datas, null);
        mLayoutId = layoutId;
        mMultiItemTypeSupport = headerItemTypeSupport;
        mSectionSupport = sectionSupport;
        mSections = new LinkedHashMap<>();
        findSections();
        registerAdapterDataObserver(observer);
    }

    private MultiItemTypeSupport<T> headerItemTypeSupport = new MultiItemTypeSupport<T>() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_SECTION)
                return mSectionSupport.sectionHeaderLayoutId();
            else
                return mLayoutId;
        }

        @Override
        public int getItemViewType(int position, T o) {
            return mSections.values().contains(position) ? TYPE_SECTION : TYPE_COMMON_ITEM;
        }
    };

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, null);
    }

    final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            findSections();
        }
    };

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
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

    }


    @Override
    public int getItemCount() {
        return super.getItemCount() + mSections.size();
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
        return position - nSections;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        int realIndex = getIndexForPosition(position);
        Log.d("djw", "position---" + position + "----realIndex---" + realIndex);
        if (holder.getItemViewType() == TYPE_SECTION) {
            holder.setText(mSectionSupport.sectionTitleTextViewId(), mSectionSupport.getTitle(mDatas.get(realIndex)));
            return;
        }
        super.onBindViewHolder(holder, realIndex);
    }
}
