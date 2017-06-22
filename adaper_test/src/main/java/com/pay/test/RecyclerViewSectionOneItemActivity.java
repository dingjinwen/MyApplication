package com.pay.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pay.base_adapter_recyclerview_library.BaseSectionOneItemTypeAdapter;
import com.pay.base_adapter_recyclerview_library.SectionSupport;
import com.pay.base_adapter_recyclerview_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/19 0019
 */

public class RecyclerViewSectionOneItemActivity extends AppCompatActivity {
    private List<String> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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

        mRecyclerView.setAdapter(new BaseSectionOneItemTypeAdapter<String>(this, R.layout.item_list, mDatas, sectionSupport) {
            @Override
            public void convert(ViewHolder holder, String o) {
                holder.setText(R.id.id_item_list_title, o);
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

        for (int i = 0; i < mDatas.size(); i++) {
            Log.d("djw", "data----" + mDatas.get(i));
        }
    }
}
