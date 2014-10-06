package com.renyu.swipebacklayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.renyu.swipebacklayout.myview.SwipeBackLayout;
import com.renyu.swipebacklayout.myview.SwipeBackLayout.OnSlideFinishListener;

public class SwipeScrollActivity extends Activity {
	
	SwipeBackLayout scroll_swipe=null;
	ScrollView scroll_scrollview=null;
	TextView scroll_testview=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_swipescroll);
		
		scroll_scrollview=(ScrollView) findViewById(R.id.scroll_scrollview);
		scroll_swipe=(SwipeBackLayout) findViewById(R.id.scroll_swipe);
		scroll_swipe.setOnSlideFinishListener(new OnSlideFinishListener() {
			
			@Override
			public void onSlideFinish() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		scroll_testview=(TextView) findViewById(R.id.scroll_testview);
		scroll_testview.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SwipeScrollActivity.this, "µã»÷ÁË", Toast.LENGTH_SHORT).show();
			}});
	}
}
