package com.pay.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.pay.base_adapter_list_library.BaseSectionOneItemTypeAdapter;
import com.pay.base_adapter_list_library.SectionSupport;
import com.pay.base_adapter_list_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/17 0017
 */

public class ListViewSectionOneItemActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<>();
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mListView = ((ListView) findViewById(R.id.id_listview_list));

        SectionSupport<String> sectionSupport = new SectionSupport<String>() {
            @Override
            public int sectionHeaderLayoutId() {
                return R.layout.item_section;
            }

            @Override
            public int sectionTitleTextViewId() {
                return R.id.id_item_section_title;
            }

            @Override
            public String getTitle(String s) {
                return s.substring(0, s.length() - 3);
            }
        };

        mListView.setAdapter(new BaseSectionOneItemTypeAdapter<String>(this, mDatas, R.layout.item_list, sectionSupport) {
            @Override
            public void convert(int position, ViewHolder holder, String item) {
                holder.setText(R.id.id_item_list_title, item);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                mDatas.add("sectionA--" + i);
            } else {
                mDatas.add("sectionB--" + i);
            }
        }
    }
}
