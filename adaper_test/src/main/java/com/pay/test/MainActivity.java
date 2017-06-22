package com.pay.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pay.base_adapter_list_library.BaseOneItemTypeAdapter;
import com.pay.base_adapter_list_library.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<>(
            Arrays.asList("MultiItem ListView", "SectionOneItem ListView", "SectionMultiItem ListView",
                    "OneItem RecyclerView", "MultiItem RecyclerView", "SectionOneItem RecyclerView", "SectionMultiItem RecyclerView"
                    , "RecyclerViewHeaderAndFooterWrapperActivity"));
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = ((ListView) findViewById(R.id.id_listview_list));
        mListView.setAdapter(new BaseOneItemTypeAdapter<String>(this, mDatas, R.layout.item_list) {
            @Override
            public void convert(int position, ViewHolder helper, String item) {
                helper.setText(R.id.id_item_list_title, item);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, ListViewMultiItemActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ListViewSectionOneItemActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, ListViewSectionMultiItemActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, RecyclerViewMultiItemActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, RecyclerViewSectionOneItemActivity.class);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, RecyclerViewSectionMultiItemActivity.class);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this, RecyclerViewHeaderAndFooterWrapperActivity.class);
                        break;
                    default:
                        break;
                }
                if (intent != null)
                    startActivity(intent);
            }
        });
    }

}
