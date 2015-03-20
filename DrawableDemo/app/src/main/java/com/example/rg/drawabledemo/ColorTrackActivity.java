package com.example.rg.drawabledemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.rg.drawabledemo.myview.ColorTrackView;

import java.util.ArrayList;

/**
 * Created by RG on 2015/3/16.
 * 仿今日头条导航条切换效果
 */
public class ColorTrackActivity extends FragmentActivity {

    ViewPager viewpager=null;
    ColorTrackView colorTrackView1=null;
    ColorTrackView colorTrackView2=null;
    ColorTrackView colorTrackView3=null;
    ColorTrackView colorTrackView4=null;
    ArrayList<ColorTrackView> tabs=null;

    ArrayList<Fragment> fragmentArrayList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colortrack);

        fragmentArrayList=new ArrayList<Fragment>();
        TabFragment f1=TabFragment.getInstance(Color.RED);
        fragmentArrayList.add(f1);
        TabFragment f2=TabFragment.getInstance(Color.WHITE);
        fragmentArrayList.add(f2);
        TabFragment f3=TabFragment.getInstance(Color.YELLOW);
        fragmentArrayList.add(f3);
        TabFragment f4=TabFragment.getInstance(Color.BLUE);
        fragmentArrayList.add(f4);

        tabs=new ArrayList<ColorTrackView>();

        init();
    }

    private void init() {

        colorTrackView1=(ColorTrackView) findViewById(R.id.colorTrackView1);
        colorTrackView1.setRefresh(100);
        colorTrackView2=(ColorTrackView) findViewById(R.id.colorTrackView2);
        colorTrackView3=(ColorTrackView) findViewById(R.id.colorTrackView3);
        colorTrackView4=(ColorTrackView) findViewById(R.id.colorTrackView4);
        tabs.add(colorTrackView1);
        tabs.add(colorTrackView2);
        tabs.add(colorTrackView3);
        tabs.add(colorTrackView4);

        viewpager=(ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter=new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                if (v>0) {
                    ColorTrackView left=tabs.get(i);
                    left.setDirection(2);
                    left.setRefresh(v*100);
                    ColorTrackView right=tabs.get(i+1);
                    right.setDirection(1);
                    right.setRefresh(v*100);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            return fragmentArrayList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    };
}


