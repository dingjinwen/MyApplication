package com.pay.base_adapter_recyclerview_library;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public interface SectionSupport<T> {
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
