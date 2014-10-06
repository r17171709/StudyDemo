package com.renyu.swipebacklayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.renyu.swipebacklayout.myview.SwipeBackLayout;
import com.renyu.swipebacklayout.myview.SwipeBackLayout.OnSlideFinishListener;

public class SwipeNormalActivity extends Activity {
	
	SwipeBackLayout normal_swipe=null;
	TextView normal_testview=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_swipenormal);
		
		normal_testview=(TextView) findViewById(R.id.normal_testview);
		normal_testview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(SwipeNormalActivity.this, "µã»÷ÁË", Toast.LENGTH_SHORT).show();
			}});
		
		normal_swipe=(SwipeBackLayout) findViewById(R.id.normal_swipe);
		normal_swipe.setOnSlideFinishListener(new OnSlideFinishListener() {
			
			@Override
			public void onSlideFinish() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
