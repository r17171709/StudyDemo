package com.renyu.myhorizontalscrollviewslidingmenu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.renyu.myhorizontalscrollviewslidingmenu.myview.SlidingMenuView;


public class MyActivity extends ActionBarActivity {

    TextView menu_control=null;
    SlidingMenuView slidingmenu=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        slidingmenu=(SlidingMenuView) findViewById(R.id.slidingmenu);
        menu_control=(TextView) findViewById(R.id.menu_control);
        menu_control.setOnClickListener(new TextView.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (slidingmenu.isOpen()) {
                    slidingmenu.close();
                }
                else {
                    slidingmenu.open();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
