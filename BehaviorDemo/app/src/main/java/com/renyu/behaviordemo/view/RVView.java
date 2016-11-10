package com.renyu.behaviordemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.renyu.behaviordemo.R;
import com.renyu.behaviordemo.adapter.RVAdapter;

import java.util.ArrayList;

/**
 * Created by renyu on 2016/11/8.
 */

public class RVView extends FrameLayout {

    Context context;

    RecyclerView rv_rv;
    RVAdapter adapter;
    TextView rv_head;

    int head_bg=0;
    String head_title;

    String[] values=new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    ArrayList<String> models;

    int headHeight=0;

    public RVView(Context context) {
        this(context, null);
    }

    public RVView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        models=new ArrayList<>();
        for (String s : values) {
            models.add(s);
        }

        TypedArray value=context.obtainStyledAttributes(attrs, R.styleable.cardrv);
        head_bg=value.getColor(R.styleable.cardrv_head_bg, Color.TRANSPARENT);
        head_title=value.getString(R.styleable.cardrv_head_title);
        value.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_rv, this);
        rv_rv= (RecyclerView) findViewById(R.id.rv_rv);
        rv_rv.setHasFixedSize(true);
        rv_rv.setLayoutManager(new LinearLayoutManager(context));
        adapter=new RVAdapter(context, models);
        rv_rv.setAdapter(adapter);
        rv_head= (TextView) findViewById(R.id.rv_head);
        rv_head.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return rv_rv.onTouchEvent(motionEvent);
            }
        });
        rv_head.setText(head_title);
        rv_head.setBackgroundColor(head_bg);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        headHeight=dp2Px(context, 50);
    }

    /**
     * 顶部head的高度，后续将用此作为每一个视图的偏移量
     * @return
     */
    public int getHeadHeight() {
        return dp2Px(context, 50);
    }

    private int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
