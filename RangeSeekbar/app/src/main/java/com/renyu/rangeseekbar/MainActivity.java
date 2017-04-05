package com.renyu.rangeseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.renyu.rangeseekbar.myview.RangeSeekbar;

public class MainActivity extends AppCompatActivity {

    RangeSeekbar range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        range= (RangeSeekbar) findViewById(R.id.range);
        range.post(new Runnable() {
            @Override
            public void run() {
                range.setPercent(11, 74);
            }
        });
        range.setOnChangeListener(new RangeSeekbar.OnChangeListener() {
            @Override
            public void onValueChange(int thumbL, int thumbR) {
                Log.d("MainActivity", thumbL + " " + thumbR);
            }
        });
    }
}
