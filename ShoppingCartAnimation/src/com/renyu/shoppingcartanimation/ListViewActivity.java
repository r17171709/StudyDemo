package com.renyu.shoppingcartanimation;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;


public class ListViewActivity extends ActionBarActivity {

    ImageView car=null;
    ListView activity_listview=null;
    ViewGroup animationLayout=null;
    ImageView imageView_animation=null;

    ArrayList<String> strs=null;
    ActivityAdapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        strs=new ArrayList<String>();
        for (int i=0;i<30;i++) {
            strs.add(""+i);
        }
        adapter=new ActivityAdapter(ListViewActivity.this, strs);
        activity_listview=(ListView) findViewById(R.id.activity_listview);
        activity_listview.setAdapter(adapter);
        car=(ImageView) findViewById(R.id.car);
        animationLayout=createAnimateLayout();
    }

    /**
     * 创建动画平移层
     * @return
     */
    public ViewGroup createAnimateLayout() {
        ViewGroup windowView=(ViewGroup) getWindow().getDecorView();
        LinearLayout layout=new LinearLayout(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setBackgroundColor(Color.TRANSPARENT);
        windowView.addView(layout);
        return layout;
    }

    /**
     * 在动画平移层上面执行动画效果
     * @param startLocation
     */
    public void doAnimation(int[] startLocation, int width, int height) {
        //从起始位置创建小红点
        imageView_animation=new ImageView(this);
        imageView_animation.setImageResource(R.drawable.sign);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin=startLocation[0];
        params.topMargin=startLocation[1];
        imageView_animation.setLayoutParams(params);
        animationLayout.addView(imageView_animation);
        //计算得到小红点最终位置
        int endLocation[]=new int[2];
        car.getLocationInWindow(endLocation);
        //执行动画效果，起始终止点均为图片中心点
        TranslateAnimation animation=new TranslateAnimation(width/2, endLocation[0]+car.getWidth()/2-startLocation[0], height/2, endLocation[1]+car.getHeight()/2-startLocation[1]);
        animation.setDuration(500);
        animation.setRepeatCount(0);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView_animation.setVisibility(View.GONE);
                animationLayout.removeAllViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView_animation.startAnimation(animation);
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
