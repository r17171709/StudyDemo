package com.renyu.swipebacklayout;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.renyu.swipebacklayout.myview.SwipeBackLayout;
import com.renyu.swipebacklayout.myview.SwipeBackLayout.OnSlideFinishListener;

public class SwipeViewPagerActivity extends Activity {
	
	ViewPager viewpager_scrollview=null;
	ArrayList<View> views=null;
	SwipeBackLayout viewpager_swipe=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_swipeviewpager);
		
		views=new ArrayList<View>();
		for(int i=0;i<5;i++) {
			View v=LayoutInflater.from(SwipeViewPagerActivity.this).inflate(R.layout.activity_main, null);
			views.add(v);
		}
		
		viewpager_scrollview=(ViewPager) findViewById(R.id.viewpager_scrollview);
		viewpager_scrollview.setAdapter(new MyAdapter());
		viewpager_swipe=(SwipeBackLayout) findViewById(R.id.viewpager_swipe);
		viewpager_swipe.setOnSlideFinishListener(new OnSlideFinishListener() {
			
			@Override
			public void onSlideFinish() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView(views.get(position));
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View v=views.get(position);
			((ViewPager)container).addView(v);
			return v;
		}
		
	}

}
