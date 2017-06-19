package com.example.base_adapter_list_library;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public interface SectionSupport<T> {
    int sectionHeaderLayoutId();

    int sectionTitleTextViewId();

    String getTitle(T t);
}
