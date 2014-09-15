package com.renyu.arrorprogressbar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.renyu.arrorprogressbar.myview.MyLayoutProgress;


public class MyActivity extends ActionBarActivity {

    MyLayoutProgress progresslayout=null;
    TextView hello_world=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        hello_world=(TextView) findViewById(R.id.hello_world);
        hello_world.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                progresslayout.setProgress(50);
            }
        });
        progresslayout=(MyLayoutProgress) findViewById(R.id.progresslayout);

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
