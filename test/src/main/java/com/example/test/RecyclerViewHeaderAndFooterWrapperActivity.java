package com.example.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.base_adapter_recyclerview_library.BaseOneItemTypeAdapter;
import com.example.base_adapter_recyclerview_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import wrapper.HeaderAndFooterWrapper;

/**
 * @author dingjinwen
 * @description
 * @date 2017/6/19 0019
 */

public class RecyclerViewHeaderAndFooterWrapperActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private BaseOneItemTypeAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new BaseOneItemTypeAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            public void convert(final ViewHolder holder, String s) {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("djw", "第几项数据----" + holder.getItemPosition());
                    }
                });
            }
        };

        initHeaderAndFooter();

        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
    }

    private void initDatas() {
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        EditText t2 = new EditText(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addFootView(t2);

    }
}
