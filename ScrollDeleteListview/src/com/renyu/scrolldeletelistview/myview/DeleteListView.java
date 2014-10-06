package com.renyu.scrolldeletelistview.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

import com.renyu.scrolldeletelistview.R;

/**
 * Created by Administrator on 2014/9/17.
 */
public class DeleteListView extends ListView {

    //最小移动距离，以判断是否达到子视图相应标准
    int scaledEdgeSlop=0;
    //达到相应标准，即锁定listview
    static boolean isLock=false;
    //是否是滑动listview中的item
    boolean isScrollItem=false;
    int startX=0;
    int startY=0;
    int moveX=0;
    int moveY=0;

    public DeleteListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public DeleteListView(Context context) {
        super(context);
        init(context);
    }

    public DeleteListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaledEdgeSlop=ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX= (int) ev.getX();
                startY= (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX= (int) ev.getX();
                moveY= (int) ev.getY();
                if (!isLock) {
                    if(Math.abs(moveX-startX)>scaledEdgeSlop&&Math.abs(moveY-startY)<scaledEdgeSlop/2) {
                        isLock=true;
                        isScrollItem=true;
                        return false;
                    }
                    else {
                        //此处直接返回true的话，虽然可以屏蔽item的响应事件，但与此同时响应事件完全交给了onTouchEvent，造成item永远也无法获得事件，丧失必要功能
                        return super.onInterceptTouchEvent(ev);
                    }
                }
                else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
                isLock=false;
                break;
            case MotionEvent.ACTION_CANCEL:
                isLock=false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag=super.dispatchTouchEvent(ev);
        if (ev.getAction()==MotionEvent.ACTION_UP) {
            if(!flag) {
            	//普通的item点击事件
                int position=pointToPosition((int) ev.getX(), (int) ev.getY());
                View view=getChildAt(position-getFirstVisiblePosition());
                super.performItemClick(view, position, view.getId());
            }
            else if(flag&&isScrollItem) {
                int position=pointToPosition((int) ev.getX(), (int) ev.getY());
                View view=getChildAt(position-getFirstVisiblePosition());
                DeleteItem item=(DeleteItem) view.findViewById(R.id.deleteitem_layout);
                if (item.isScrollToBackWidth()) {
                    ParamsManager.openItem.put(""+position, "");
                }
                else {
                    ParamsManager.openItem.remove(""+position);
                }
            }
            isScrollItem=false;
        }
        return super.dispatchTouchEvent(ev);
    }
}
