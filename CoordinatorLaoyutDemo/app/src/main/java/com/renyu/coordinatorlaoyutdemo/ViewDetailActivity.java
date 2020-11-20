package com.renyu.coordinatorlaoyutdemo;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renyu.coordinatorlaoyutdemo.adapter.VideoListAdapter;

public class ViewDetailActivity extends AppCompatActivity {
    RecyclerView rv_detail;
    AppCompatImageView iv_detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetail);

        rv_detail = findViewById(R.id.rv_detail);
        rv_detail.setLayoutManager(new LinearLayoutManager(this));
        rv_detail.setHasFixedSize(true);
        rv_detail.setAdapter(new VideoListAdapter());

        iv_detail = findViewById(R.id.iv_detail);
        iv_detail.setImageResource(R.mipmap.timg);
        iv_detail.post(new Runnable() {
            @Override
            public void run() {
                // 不知道这里为什么要重新设置宽高
                ViewGroup.LayoutParams params = iv_detail.getLayoutParams();
                params.height = iv_detail.getMeasuredHeight();
                params.width = iv_detail.getMeasuredWidth();
                iv_detail.setLayoutParams(params);
            }
        });
    }
}
