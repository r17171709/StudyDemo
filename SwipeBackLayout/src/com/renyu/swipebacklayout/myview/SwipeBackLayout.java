package com.renyu.swipebacklayout.myview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class SwipeBackLayout extends RelativeLayout {
	
	//当前滑动视图的父视图，一般就是顶级视图，控制此视图的移动
	ViewGroup parentView=null;
	//滑动距离判断
	int viewWidth=0;
	//最小滑动距离
	int scaledEdgeSlop=0;
	//上一次触发事件的坐标
	int tempx=0;
	int tempy=0;
	//action_down的坐标
	int startX=0;
	int startY=0;
	//是否进行横向滑动
	boolean isHSlide=false;
	//是否是关闭页面
	boolean isFinish=false;
	//全部viewpager合集
	ArrayList<ViewPager> view_pagers=null;
	
	OnSlideFinishListener slideListener;
	
	Scroller scroller=null;

	public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		init(context);
	}

	public SwipeBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		init(context);
	}

	public SwipeBackLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init(context);
	}
	
	private void init(Context context) {
		view_pagers=new ArrayList<ViewPager>();
		scroller=new Scroller(context);
		scaledEdgeSlop=ViewConfiguration.get(context).getScaledEdgeSlop();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed) {
			getAllViewPager(this);
			parentView=(ViewGroup) getParent();
			viewWidth=getWidth();
		}
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//viewpager直接原样返回
		if(getTouchViewPager(ev)!=null&&getTouchViewPager(ev).getCurrentItem()>0) {
			return super.onInterceptTouchEvent(ev);
		}
		switch(ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX=(int) ev.getRawX();
			startY=(int) ev.getRawY();
			tempx=startX;
			tempy=startY;
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX=(int) ev.getRawX();
			int moveY=(int) ev.getRawY();
			tempx=moveX;
			//判断是否是执行横向从左往右滑动
			if(moveX-startX>scaledEdgeSlop&&Math.abs(moveY-startY)<scaledEdgeSlop) {
				return true;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int moveX=(int) event.getRawX();
			int moveY=(int) event.getRawY();
			int deltaX=moveX-tempx;
			tempx=moveX;
			//判断是否是执行横向滑动
			if(Math.abs((moveX-startX))>scaledEdgeSlop&&Math.abs((moveY-startY))<scaledEdgeSlop) {
				isHSlide=true;
			}
			if(moveX>startX&&isHSlide) {
				parentView.scrollBy(-deltaX, 0);
			}
					
			break;
		case MotionEvent.ACTION_UP:
			isHSlide=false;
			if(viewWidth/2>Math.abs(parentView.getScrollX())) {
				scrollToOriginal();
				isFinish=false;
			}
			else if(viewWidth/2<Math.abs(parentView.getScrollX())) {
				scrollToRight();
				isFinish=true;
			}
			break;
		}
		return true;
	}

	/**
	 * 移动到最右边
	 */
	private void scrollToRight() {
		int deltaX=viewWidth+parentView.getScrollX();
		scroller.startScroll(parentView.getScrollX(), 0, -deltaX+1, 0, Math.abs(deltaX));
		postInvalidate();
	}
	
	/**
	 * 回到初始位置
	 */
	private void scrollToOriginal() {
		int deltaX=parentView.getScrollX();
		scroller.startScroll(parentView.getScrollX(), 0, -deltaX, 0, Math.abs(deltaX));
		postInvalidate();
	}
	
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(scroller.computeScrollOffset()) {
			parentView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
			if(scroller.isFinished()) {
				if(slideListener!=null&&isFinish) {
					slideListener.onSlideFinish();
				}
			}
		}
		
	}
	
	/**
	 * 得到全部viewPager
	 * @param parent
	 */
	private void getAllViewPager(ViewGroup parent) {
		int num=parent.getChildCount();
		for(int i=0;i<num;i++) {
			View child=parent.getChildAt(i);
			if(child instanceof ViewPager) {
				view_pagers.add((ViewPager) child);
			}
			else if(child instanceof ViewGroup) {
				getAllViewPager((ViewGroup) child);
			}
		}
	}
	
	/**
	 * 得到当前点击viewpager
	 * @param ev
	 * @return
	 */
	private ViewPager getTouchViewPager(MotionEvent ev) {
		if(view_pagers.size()==0) {
			return null;
		}
		Rect rect=new Rect();
		for(int i=0;i<view_pagers.size();i++) {
			view_pagers.get(i).getHitRect(rect);
			if(rect.contains((int) ev.getX(), (int) ev.getY())) {
				return view_pagers.get(i);
			}
		}
		return null;
	}
	
	public interface OnSlideFinishListener {
		public void onSlideFinish();
	}
	
	public void setOnSlideFinishListener(OnSlideFinishListener slideListener) {
		this.slideListener=slideListener;
	}

}
