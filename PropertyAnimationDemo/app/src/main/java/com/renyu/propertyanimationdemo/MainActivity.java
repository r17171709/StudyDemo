package com.renyu.propertyanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView click=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click=(TextView) findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playWithXmlSet();
            }
        });
    }

    /**
     * 简单渐变动画演示
     */
    private void singledemo() {
        ObjectAnimator.ofFloat(click, "alpha", 1F, 0F).setDuration(1000).start();
    }

    /**
     * 联合动画效果
     */
    private void multiAnimation1() {
        ObjectAnimator ani1=ObjectAnimator.ofFloat(click, "alpha", 1F, 0F);
        ObjectAnimator ani2=ObjectAnimator.ofFloat(click, "translationX", 100, 300);
        ObjectAnimator ani3=ObjectAnimator.ofFloat(click, "translationY", 100, 300);
        AnimatorSet set=new AnimatorSet();
        //三者同步执行
        set.playTogether(ani1, ani2, ani3);
        set.setDuration(1000);
        set.start();
    }

    /**
     * 联合动画效果
     */
    private void multiAnimation2() {
        ObjectAnimator ani1=ObjectAnimator.ofFloat(click, "alpha", 1F, 0F);
        ObjectAnimator ani2=ObjectAnimator.ofFloat(click, "translationX", 100, 300);
        ObjectAnimator ani3=ObjectAnimator.ofFloat(click, "translationY", 100, 300);
        AnimatorSet set=new AnimatorSet();
        //三者顺序执行
        set.playSequentially(ani1, ani2, ani3);
        set.setDuration(1000);
        set.start();
    }

    /**
     * 联合动画效果
     */
    private void multiAnimation3() {
        ObjectAnimator ani1=ObjectAnimator.ofFloat(click, "alpha", 1F, 0F);
        ObjectAnimator ani2=ObjectAnimator.ofFloat(click, "translationX", 100, 200);
        ObjectAnimator ani3=ObjectAnimator.ofFloat(click, "translationY", 100, 200);
        AnimatorSet set=new AnimatorSet();
        //自定义动画执行顺序ani3->ani3->ani1
        set.play(ani1).after(ani2);
        set.play(ani3).before(ani2);
        set.setDuration(2000);
        set.start();
    }

    /**
     * 动画监听
     */
    private void animationListener() {
        ObjectAnimator ani1=ObjectAnimator.ofFloat(click, "alpha", 1F, 0F).setDuration(1000);
        //AnimatorListenerAdapter与animatorListener区别在于，前者可以选择性实现其中一种监听，而后者需要全部监听
        ani1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                System.out.println("动画结束了");
            }
        });
        ani1.start();
    }

    /**
     * 使用不存在的动画效果名称，去自定义视图动画
     */
    private void customerAnimation() {
        //propertyName定义一个不存在的动画效果，就得自己去实现了
        ObjectAnimator ani1=ObjectAnimator.ofFloat(click, "yd", 100, 200).setDuration(1000);
        ani1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float tranX= (float) animation.getAnimatedValue("yd");
                click.setTranslationX(tranX);
            }
        });
        ani1.start();
    }

    /**
     * 使用propertyValuesHolder去执行联合动画
     */
    private void userPropertyValues() {
        PropertyValuesHolder holder1=PropertyValuesHolder.ofFloat("alpha", 1F, 0F);
        PropertyValuesHolder holder2=PropertyValuesHolder.ofFloat("translationX", 100, 200);
        PropertyValuesHolder holder3=PropertyValuesHolder.ofFloat("translationY", 100, 200);
        ObjectAnimator.ofPropertyValuesHolder(click, holder1, holder2, holder3).setDuration(1000).start();
    }

    private void valueAnimation() {
        ValueAnimator animator=ValueAnimator.ofFloat(0, 100);
        animator.setTarget(click);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                click.setTranslationY((float) animation.getAnimatedValue());
            }
        });
    }

    /**
     * 使用TypeEvaluator自定义返回值属性
     */
    private void typeEvaluator() {
        ValueAnimator animator=new ValueAnimator();
        animator.setDuration(2000);
        animator.setObjectValues(new PointF(0, 0));
        animator.setInterpolator(new LinearInterpolator());
        animator.setEvaluator(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                System.out.println(fraction);
                PointF pointF=new PointF();
                //随便写的自定义位置
                pointF.x=100*fraction*3;
                pointF.y=0.5f*100*fraction*3*fraction*3;
                return pointF;
            }
        });
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF= (PointF) animation.getAnimatedValue();
                click.setTranslationX(pointF.x);
                click.setTranslationY(pointF.y);
            }
        });
    }

    //使用xml文件来创建属性动画
    private void playWithXml() {
        Animator animator=AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scalex);
        animator.setTarget(click);
        animator.start();
    }

    private void playWithXmlSet () {
        Animator animator=AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scale);
        animator.setTarget(click);
        click.setPivotX(0);
        click.setPivotY(0);
        animator.start();
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
