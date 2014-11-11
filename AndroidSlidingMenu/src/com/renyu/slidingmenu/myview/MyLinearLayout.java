package com.renyu.slidingmenu.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout
{

	public MyLinearLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setChildrenDrawingOrderEnabled(true);
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i)
	{

		if (i == 0)
			return 0;
		if (i == 2)
			return 1;
		if (i == 1)
			return 2;
		return super.getChildDrawingOrder(childCount, i);

	}

}
