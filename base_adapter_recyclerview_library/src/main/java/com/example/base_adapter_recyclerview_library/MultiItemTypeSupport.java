package com.example.base_adapter_recyclerview_library;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
