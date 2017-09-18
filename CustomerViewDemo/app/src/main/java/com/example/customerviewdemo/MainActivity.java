package com.example.customerviewdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customerviewdemo.myview.BaiduRouteView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager vp_main;
    ArrayList<Fragment> fragments;
    MainFragmentAdapter adapter;
    BaiduRouteView r_view_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments=new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new MainFragment());
        fragments.add(new MainFragment());

        vp_main= (ViewPager) findViewById(R.id.vp_main);
        adapter=new MainFragmentAdapter(getSupportFragmentManager());
        vp_main.setAdapter(adapter);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                r_view_main.setInnerRv(((MainFragment) fragments.get(position)).getRv_main());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        r_view_main= (BaiduRouteView) findViewById(R.id.r_view_main);
        vp_main.post(new Runnable() {
            @Override
            public void run() {
                r_view_main.setInnerRv(((MainFragment) fragments.get(0)).getRv_main());
            }
        });
    }

    public class MainFragmentAdapter extends FragmentPagerAdapter {

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
