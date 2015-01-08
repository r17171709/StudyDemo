package com.renyu.weixintabdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.renyu.weixintabdemo.myview.BarItemImageView;
import com.renyu.weixintabdemo.myview.ImageTextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<View> views=null;

    int[] colors={Color.YELLOW, Color.RED, Color.BLACK, Color.GREEN};
    int[] selectImage={R.drawable.tabbar_home_selected, R.drawable.tabbar_message_center_selected, R.drawable.tabbar_discover_selected, R.drawable.tabbar_profile_selected};
    int[] normalImage={R.drawable.tabbar_home, R.drawable.tabbar_message_center, R.drawable.tabbar_discover, R.drawable.tabbar_profile};

    ViewPager main_viewPager=null;
    LinearLayout main_trip=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views=new ArrayList<View>();
        for(int i=0;i<4;i++) {
            View view=LayoutInflater.from(this).inflate(R.layout.emptyview, null);
            view.setBackgroundColor(colors[i]);
            views.add(view);
        }
        main_viewPager=(ViewPager) findViewById(R.id.main_viewPager);
        main_viewPager.setAdapter(new MyAdapter());
        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                if(i<views.size()-1) {
                    ImageTextView it1=(ImageTextView) main_trip.getChildAt(i);
                    BarItemImageView imageView1=it1.getTabitem_image();
                    imageView1.setBarAlpha((int) (255*(1-v)));
                    it1.setBarColor(1-v);

                    ImageTextView it2=(ImageTextView) main_trip.getChildAt(i+1);
                    BarItemImageView imageView2=it2.getTabitem_image();
                    imageView2.setBarAlpha((int) (255*v));
                    it2.setBarColor(v);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        main_trip=(LinearLayout) findViewById(R.id.main_trip);
        for (int i=0;i<main_trip.getChildCount();i++) {
            ImageTextView imageTextView=(ImageTextView) main_trip.getChildAt(i);
            BarItemImageView barItemImageView=imageTextView.getTabitem_image();
            barItemImageView.setNormalImage(BitmapFactory.decodeResource(getResources(), normalImage[i]));
            barItemImageView.setSelectImage(BitmapFactory.decodeResource(getResources(), selectImage[i]));
            if (i==0) {
                barItemImageView.setBarAlpha(255);
                imageTextView.setBarColor(1);
            }
            else {
                barItemImageView.setBarAlpha(0);
                imageTextView.setBarColor(0);
            }
        }
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

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
