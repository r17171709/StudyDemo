package com.renyu.stickyheaderview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by renyu on 2016/12/21.
 */

public class MainListActivity extends AppCompatActivity {

    TextView header_view;
    ListView lv_sticky;
    MainListAdapter adapter;

    ArrayList<String> strings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lv);

        strings=new ArrayList<>();
        for (int i=0;i<30;i++) {
            strings.add(""+i);
            strings.add(""+i);
        }

        header_view= (TextView) findViewById(R.id.header_view);
        lv_sticky= (ListView) findViewById(R.id.lv_sticky);
        adapter=new MainListAdapter(strings, this);
        lv_sticky.setAdapter(adapter);
        lv_sticky.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View stickyInfoView=view.getChildAt(0);
                if (stickyInfoView!=null && stickyInfoView.getContentDescription()!=null) {
                    header_view.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }
                int index_=view.pointToPosition(header_view.getMeasuredWidth()/2, header_view.getMeasuredHeight()+1);
                View transInfoView=view.getChildAt(index_-firstVisibleItem);
                if (transInfoView!=null && transInfoView.findViewById(R.id.header_view).getTag()!=null) {
                    int tag= (int) transInfoView.findViewById(R.id.header_view).getTag();
                    int deltaY=transInfoView.getTop()-header_view.getMeasuredHeight();
                    if (tag==MainListAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop()>0) {
                            header_view.setTranslationY(deltaY);
                        }
                        else {
                            header_view.setTranslationY(0);
                        }
                    }
                    else {
                        header_view.setTranslationY(0);
                    }
                }
            }
        });
    }
}
