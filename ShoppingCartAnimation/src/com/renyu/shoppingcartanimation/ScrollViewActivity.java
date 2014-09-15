package com.renyu.shoppingcartanimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2014/9/15.
 */
public class ScrollViewActivity extends Activity {

    ImageView car=null;
    ImageView scrollview_image=null;
    ViewGroup animationLayout=null;
    ImageView imageView_animation=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

        car=(ImageView) findViewById(R.id.car);
        scrollview_image=(ImageView) findViewById(R.id.scrollview_image);
        scrollview_image.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //测量起始位置位置
                int[] startLocation=new int[2];
                view.getLocationInWindow(startLocation);
                doAnimation(startLocation, view.getWidth(), view.getHeight());
            }
        });
        animationLayout=createAnimateLayout();
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

    /**
     * 创建动画平移层
     * @return
     */
    private ViewGroup createAnimateLayout() {
        ViewGroup windowView=(ViewGroup) getWindow().getDecorView();
        LinearLayout layout=new LinearLayout(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setBackgroundColor(Color.TRANSPARENT);
        windowView.addView(layout);
        return layout;
    }
}
