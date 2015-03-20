package com.example.rg.drawabledemo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.rg.drawabledemo.myview.CircleImageDrawable;
import com.example.rg.drawabledemo.myview.ColorTrackView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageview1=(ImageView) findViewById(R.id.imageview1);
        imageview1.setImageDrawable(new CircleImageDrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.sheying0311)));

        new Thread(runnable).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ColorTrackView colorTrackView=(ColorTrackView) findViewById(R.id.colorTrackView);
            ObjectAnimator oa=ObjectAnimator.ofFloat(colorTrackView, "refresh", 100, 0);
            oa.setDuration(2000);
            oa.start();

        }
    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    };

}
