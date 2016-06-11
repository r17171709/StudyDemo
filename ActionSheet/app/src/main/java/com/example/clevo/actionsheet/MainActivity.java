package com.example.clevo.actionsheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button main_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置了相同属性，此处将不起作用
//        setTheme(R.style.themeStyle2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_button= (Button) findViewById(R.id.main_button);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidActionSheetFragment.build(getSupportFragmentManager()).setChoice(AndroidActionSheetFragment.Builder.CHOICE.ITEM).setTitle("标题").setTag("MainActivity")
                        .setItems(new String[]{"1", "2", "3", "4", "5", "6"}).setOnItemClickListener(new AndroidActionSheetFragment.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                }).show();
//                AndroidActionSheetFragment.build(getSupportFragmentManager()).setChoice(AndroidActionSheetFragment.Builder.CHOICE.GRID).setTitle("标题").setTag("MainActivity")
//                        .setItems(new String[]{"QQ", "微信", "微博", "Facebook", "Twitter"}).setImages(
//                        new int[]{R.mipmap.ic_qq, R.mipmap.ic_wechat, R.mipmap.ic_sina, R.mipmap.ic_share_facebook, R.mipmap.ic_share_twitter}).setOnItemClickListener(new AndroidActionSheetFragment.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//
//                    }
//                }).show();
            }
        });
    }
}
