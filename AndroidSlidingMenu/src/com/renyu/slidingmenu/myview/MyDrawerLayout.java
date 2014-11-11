package com.renyu.slidingmenu.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ry on 2014/11/11.
 */
public class MyDrawerLayout extends LinearLayout {

    public MyDrawerLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i)
    {

        if (i == 0)
            return 1;
        if (i == 1)
            return 0;
        if (i == 2)
            return 2;
        return super.getChildDrawingOrder(childCount, i);

    }
}
