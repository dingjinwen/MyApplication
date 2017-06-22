package com.pay.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.pay.base_adapter_list_library.BaseSectionMultiItemTypeAdapter;
import com.pay.base_adapter_list_library.MultiItemTypeSupport;
import com.pay.base_adapter_list_library.SectionSupport;
import com.pay.base_adapter_list_library.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import bean.MultiTypeBean;

public class ListViewSectionMultiItemActivity extends AppCompatActivity {

    private List<MultiTypeBean> mDatas = new ArrayList<>();
    private ListView mListView;

    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mListView = ((ListView) findViewById(R.id.id_listview_list));

        SectionSupport<MultiTypeBean> sectionSupport = new SectionSupport<MultiTypeBean>() {
            @Override
            public int sectionHeaderLayoutId() {
                return R.layout.item_section;
            }

            @Override
            public int sectionTitleTextViewId() {
                return R.id.id_item_section_title;
            }

            @Override
            public String getTitle(MultiTypeBean multiTypeBean) {
                if (multiTypeBean == null) {
                    Log.d("djw", "getTitle---data----" + multiTypeBean);
                }
                String content = multiTypeBean.getContent();
                return content.substring(0, content.length() - 3);
            }
        };

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

        mListView.setAdapter(new BaseSectionMultiItemTypeAdapter<MultiTypeBean>(this, mDatas, multiItemTypeSupport, sectionSupport) {
            @Override
            public void convert(int position, ViewHolder holder, MultiTypeBean item) {
                switch (multiItemTypeSupport.getItemViewType(position, item)) {
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
            if (i < 5) {
                MultiTypeBean bean = new MultiTypeBean();
                bean.setType(1);
                bean.setContent("sectionA--" + i);
                mDatas.add(bean);
            } else {
                MultiTypeBean bean = new MultiTypeBean();
                bean.setType(2);
                bean.setContent("sectionB--" + i);
                mDatas.add(bean);
            }
        }
    }
}
