package com.renyu.behaviordemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by renyu on 2016/11/11.
 */

public class BottomSheetActivity extends AppCompatActivity {

    boolean isChangeHeight;

    CoordinatorLayout design_coor;
    Toolbar design_toolbar;
    RelativeLayout design_bottom_sheet;

    BottomSheetBehavior behavior;

    int beginHeight=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet);

        design_coor= (CoordinatorLayout) findViewById(R.id.design_coor);
        design_toolbar= (Toolbar) findViewById(R.id.design_toolbar);
        design_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        design_bottom_sheet= (RelativeLayout) findViewById(R.id.design_bottom_sheet);
        behavior=BottomSheetBehavior.from(design_bottom_sheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float tranY=0;
                float scrollY=slideOffset*(design_coor.getHeight()-design_toolbar.getHeight());
                if (scrollY>design_coor.getHeight()-design_toolbar.getHeight()*2) {
                    tranY=(scrollY-(design_coor.getHeight()-design_toolbar.getHeight()*2));
                }
                else {
                    tranY=0;
                }
                design_toolbar.setTranslationY(-tranY);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isChangeHeight) {
            //为toolbar预留空间
            CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) design_bottom_sheet.getLayoutParams();
            params.height=design_coor.getHeight()-design_toolbar.getHeight();
            design_bottom_sheet.setLayoutParams(params);

            beginHeight=design_toolbar.getHeight()*2;

            isChangeHeight=true;
        }
    }
}
