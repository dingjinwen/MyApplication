package com.pay.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pay.base_adapter_list_library.BaseMultiItemTypeAdapter;
import com.pay.base_adapter_list_library.MultiItemTypeSupport;
import com.pay.base_adapter_list_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import bean.MultiTypeBean;

public class ListViewMultiItemActivity extends AppCompatActivity {

    private List<MultiTypeBean> mDatas = new ArrayList<>();
    private ListView mListView;

    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mListView = (ListView) findViewById(R.id.id_listview_list);
        mListView.setDivider(null);
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

            @Override
            public int getViewTypeCount() {
                return 2;
            }
        };
        mListView.setAdapter(new BaseMultiItemTypeAdapter<MultiTypeBean>(this, mDatas, multiItemTypeSupport) {
            @Override
            public void convert(final int position, ViewHolder holder, MultiTypeBean item) {
                int type = multiItemTypeSupport.getItemViewType(position, item);
                switch (type) {
                    case TYPE_ONE:
                    default:
                        holder.setText(R.id.chat_from_content, item.getContent());
                        break;
                    case TYPE_TWO:
                        holder.setText(R.id.chat_send_content, item.getContent());
                        break;
                }
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("djw", "第几项数据----" + position);
//                    }
//                });
            }
        });

//        initHeaderAndFooter();
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

    private void initHeaderAndFooter() {
        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        EditText t2 = new EditText(this);
        t2.setText("Header 2");
        mListView.addHeaderView(t1);
        mListView.addFooterView(t2);
    }

}
