package com.renyu.tabchoiceview;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;

import com.renyu.tabchoiceview.choiceview.ChoiceView;
import com.renyu.tabchoiceview.choiceview.ExpandTabView;

public class MainActivity extends ActionBarActivity {
	
	ExpandTabView expandtab_view=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		expandtab_view=(ExpandTabView) findViewById(R.id.expandtab_view);
		ArrayList<String> titleArray=new ArrayList<String>();
		titleArray.add("标题1");
		titleArray.add("标题2");
		titleArray.add("标题3");
		ArrayList<RelativeLayout> viewArray=new ArrayList<RelativeLayout>();
		ChoiceView v1=new ChoiceView(MainActivity.this);
		v1.setText("标题1");
		viewArray.add(v1);
		ChoiceView v2=new ChoiceView(MainActivity.this);
		v2.setText("标题2");
		viewArray.add(v2);
		ChoiceView v3=new ChoiceView(MainActivity.this);
		v3.setText("标题3");
		viewArray.add(v3);
		expandtab_view.setLayoutView(titleArray, viewArray);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
