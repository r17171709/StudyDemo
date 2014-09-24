package com.renyu.myfloatscrollview.myview;

import com.renyu.myfloatscrollview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
	
	//悬浮视图初始top位置
	int floatPos=0;

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		ViewGroup parent=(ViewGroup) getChildAt(0);
		int childNum=parent.getChildCount();
		for(int i=0;i<childNum;i++) {
			//需要在xml中配置好浮动视图以及被黏贴的视图，将被黏贴的视图坐标设置为浮动视图坐标
			if(parent.getChildAt(i).getId()==R.id.target_view) {
				floatPos=parent.getChildAt(i).getTop();
			}
			if(parent.getChildAt(i).getId()==R.id.float_view) {
				parent.getChildAt(i).layout(parent.getChildAt(i).getLeft(), floatPos, parent.getChildAt(i).getLeft()+parent.getChildAt(i).getMeasuredWidth(), floatPos+parent.getChildAt(i).getMeasuredHeight());
			}
		}
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		ViewGroup parent=(ViewGroup) getChildAt(0);
		int childNum=parent.getChildCount();
		for(int i=0;i<childNum;i++) {
			if(parent.getChildAt(i).getId()==R.id.float_view) {
				//此处layout的坐标是相对于父视图的，所以在滑动到屏幕的最上方之前，需要维持之前的坐标，一旦滑动超过屏幕最上方，需要通过改变top值，使其维持在屏幕最上方
				int startY=t<floatPos?floatPos:t;
				parent.getChildAt(i).layout(parent.getChildAt(i).getLeft(), startY, parent.getChildAt(i).getLeft()+parent.getChildAt(i).getMeasuredWidth(), startY+parent.getChildAt(i).getMeasuredHeight());				
			}
		}
	}

}
