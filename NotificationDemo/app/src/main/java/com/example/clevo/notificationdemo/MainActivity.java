package com.example.clevo.notificationdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button normal;
    Button moretext;
    Button bigpic;
    Button customer;
    Button list;
    Button doubleaction;
    Button progress;

    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestCode涉及到通知的intent是否相同
        pendingIntent=PendingIntent.getActivity(MainActivity.this, (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, OpenActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        normal= (Button) findViewById(R.id.normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1000);
                utils.sendSingleLineNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });

        moretext= (Button) findViewById(R.id.moretext);
        moretext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1001);
                utils.sendMoreLineNotification("ticker", "title"
                        , "contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent"
                        , R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });
        bigpic= (Button) findViewById(R.id.bigpic);
        bigpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1002);
                utils.sendBigPicNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });
        customer= (Button) findViewById(R.id.customer);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent remotePending=PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, ShareActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews contentView=new RemoteViews(getPackageName(), R.layout.remoteview);
                contentView.setTextViewText(R.id.share_content, "这是自定义的view");
                contentView.setOnClickPendingIntent(R.id.share_facebook, remotePending);
                contentView.setOnClickPendingIntent(R.id.share_twitter, remotePending);
                //用来调用子View中需要一个Int型参数的方法
                contentView.setInt(R.id.share_content, "setTextColor", NotificationUtils.isDarkNotificationTheme(MainActivity.this)==true?Color.WHITE:Color.BLACK);

                RemoteViews bigContentView=new RemoteViews(getPackageName(), R.layout.bigcontentview);
                bigContentView.setTextViewText(R.id.share_content, "这是自定义的view");
                bigContentView.setOnClickPendingIntent(R.id.share_facebook, remotePending);
                bigContentView.setOnClickPendingIntent(R.id.share_twitter, remotePending);
                bigContentView.setInt(R.id.share_content, "setTextColor", NotificationUtils.isDarkNotificationTheme(MainActivity.this)==true?Color.WHITE:Color.BLACK);

                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1003);
                utils.sendCustomerNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, contentView, bigContentView, true, true, true);
            }
        });
        list= (Button) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> contents=new ArrayList<>();
                contents.add("一部好的电影，可以改变你对世界的看法！");
                contents.add("程序员必知的七个图形工具");
                contents.add("Splash启动界面秒开的正确打开模式");
                contents.add("全栈工程师如何快速构建一个Web应用");
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1004);
                utils.sendListNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, contents, true, true, true);
            }
        });
        doubleaction= (Button) findViewById(R.id.doubleaction);
        doubleaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent piLeft=PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, ShareActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent piRight=PendingIntent.getActivity(MainActivity.this, 0, new Intent(MainActivity.this, ShareActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1005);
                utils.sendActionNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, R.mipmap.ic_launcher, "facebook", piLeft, R.mipmap.ic_launcher, "twitter", piRight, true, true, true);
            }
        });
        progress= (Button) findViewById(R.id.progress);
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils utils=new NotificationUtils(MainActivity.this, 1006);
                utils.sendProgressNotification("ticker", "title", "content", R.mipmap.ic_launcher, pendingIntent, true, true, true);
            }
        });
    }
}
