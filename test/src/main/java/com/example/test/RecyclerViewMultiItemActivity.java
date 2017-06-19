package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.base_adapter_recyclerview_library.BaseMultiItemTypeAdapter;
import com.example.base_adapter_recyclerview_library.MultiItemTypeSupport;
import com.example.base_adapter_recyclerview_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import bean.MultiTypeBean;


public class RecyclerViewMultiItemActivity extends AppCompatActivity {

    private List<MultiTypeBean> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MultiItemTypeSupport<MultiTypeBean> multiItemTypeSupport = new MultiItemTypeSupport<MultiTypeBean>() {
            @Override
            public int getLayoutId(int itemType) {
                switch (itemType) {
                    case TYPE_ONE:
                    default:
                        return R.layout.main_chat_from_msg;
                    case TYPE_TWO:
                        return R.layout.main_chat_send_msg;
                }
            }

            @Override
            public int getItemViewType(int position, MultiTypeBean multiTypeBean) {
                int type = multiTypeBean.getType();
                switch (type) {
                    case 1:
                    default:
                        return TYPE_ONE;
                    case 2:
                        return TYPE_TWO;
                }
            }
        };
        mRecyclerView.setAdapter(new BaseMultiItemTypeAdapter<MultiTypeBean>(this, mDatas, multiItemTypeSupport) {
            @Override
            public void convert(ViewHolder holder, MultiTypeBean item) {
                int type = holder.getItemViewType();
                switch (type) {
                    case TYPE_ONE:
                    default:
                        holder.setText(R.id.chat_from_content, item.getContent());
                        break;
                    case TYPE_TWO:
                        holder.setText(R.id.chat_send_content, item.getContent());
                        break;
                }
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                MultiTypeBean bean = new MultiTypeBean();
                bean.setType(1);
                bean.setContent("第几个数---" + i);
                mDatas.add(bean);
            } else {
                MultiTypeBean bean = new MultiTypeBean();
                bean.setType(2);
                bean.setContent("第几个数---" + i);
                mDatas.add(bean);
            }
        }
    }


}
