package com.example.rg.bezierdemo;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.rg.bezierdemo.myview.BezierView;


public class MainActivity extends Activity {

    ImageView message_imageview=null;
    BezierView image_imageview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message_imageview=(ImageView) findViewById(R.id.message_imageview);
        message_imageview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        int[] location=new int[2];
                        Rect rect=new Rect();
                        //获取tipImageView的宽高
                        message_imageview.getDrawingRect(rect);
                        //获取tipImageView的位置x y
                        message_imageview.getLocationOnScreen(location);
                        //得到当前tipImageView的完整区域
                        rect.left=location[0];
                        rect.top=location[1];
                        rect.right=location[0]+rect.right;
                        rect.bottom=location[1]+rect.bottom;
                        //处于点击范围内，则执行
                        if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                            image_imageview.setVisibility(View.VISIBLE);
                            //此处为message_imageview的原始位置，为了image_imageview将拖动位置覆盖住，在原始位置的基础上，加了图片的一半宽高
                            image_imageview.touchDown((int) (getResources().getDimension(R.dimen.bezier_leftmargin)+getResources().getDimension(R.dimen.widthOffset)/2), (int) (getResources().getDimension(R.dimen.bezier_topmargin)+getResources().getDimension(R.dimen.heightOffset)/2));
                            message_imageview.setVisibility(View.GONE);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        image_imageview.touchMove((int) event.getX(), (int) event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        image_imageview.touchUp();
                        image_imageview.setVisibility(View.GONE);
                        message_imageview.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        image_imageview=(BezierView) findViewById(R.id.image_imageview);
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
}
