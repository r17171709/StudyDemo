package com.renyu.swipebacklayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.renyu.swipebacklayout.myview.SwipeBackLayout;
import com.renyu.swipebacklayout.myview.SwipeBackLayout.OnSlideFinishListener;

public class SwipeListViewActivity extends Activity {
	
	String[] array = { "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
			"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
			"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
			"1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1" };
	
	ListView list_scrollview=null;
	ArrayAdapter<String> adapter=null;
	SwipeBackLayout list_swipe=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_swipelistview);
		
		list_scrollview=(ListView) findViewById(R.id.list_scrollview);
		list_scrollview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(SwipeListViewActivity.this, "µã»÷ÁË", Toast.LENGTH_SHORT).show();
			}
		});
		adapter=new ArrayAdapter<>(SwipeListViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, array);
		list_scrollview.setAdapter(adapter);
		list_swipe=(SwipeBackLayout) findViewById(R.id.list_swipe);
		list_swipe.setOnSlideFinishListener(new OnSlideFinishListener() {
			
			@Override
			public void onSlideFinish() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	

}
