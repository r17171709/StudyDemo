package com.example.clevo.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Clevo on 2016/6/14.
 */
public class NotificationUtils {

    NotificationManager manager;
    NotificationCompat.Builder builder;

    int notificationId=-1;

    Context context;

    public NotificationUtils(Context context, int notificationId) {
        this.context=context;
        this.notificationId=notificationId;

        manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(context);
    }

    public void sample(String ticker, String title, String content, int smallIcon, PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        //状态栏文字
        builder.setTicker(ticker);
        //通知栏标题
        builder.setContentTitle(title);
        //通知栏内容
        builder.setContentText(content);
        //触发的intent
        builder.setContentIntent(intent);
        //这边设置颜色，可以给5.0及以上版本smallIcon设置背景色
        builder.setColor(Color.RED);
        //小图标
        builder.setSmallIcon(smallIcon);
        //大图标(这边同时设置了小图标跟大图标，在5.0及以上版本通知栏里面的样式会有所不同)
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        //设置该条通知时间
        builder.setWhen(System.currentTimeMillis());
        //设置为true，点击该条通知会自动删除，false时只能通过滑动来删除
        builder.setAutoCancel(true);
        //设置优先级，级别高的排在前面
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        int defaults=0;
        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        //设置声音、闪光、震动
        builder.setDefaults(defaults);
        //设置是否为一个正在进行中的通知，这一类型的通知将无法删除
        builder.setOngoing(true);
    }

    /**
     * 单行文本使用
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendSingleLineNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        sample(ticker, title ,content, smallIcon, intent, sound, vibrate, lights);
        Notification notification=builder.build();
        send(notification);
    }

    /**
     * 多行文本使用
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendMoreLineNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(context, "您的手机低于Android 4.1.2，不支持！！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        Notification notification=new NotificationCompat.BigTextStyle(builder).bigText(content).build();
        send(notification);
    }

    /**
     * 大图模式
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendBigPicNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(context, "您的手机低于Android 4.1.2，不支持！！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        //大图
        Bitmap bigPicture=BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        //图标
        Bitmap bigLargeIcon=BitmapFactory.decodeResource(context.getResources(), R.mipmap.android_bigicon);
        Notification notification=new NotificationCompat.BigPictureStyle(builder).bigLargeIcon(bigLargeIcon).bigPicture(bigPicture).build();
        send(notification);
    }

    /**
     * 自定义通知视图
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param contentView
     * @param bigContentView
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendCustomerNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, RemoteViews contentView, RemoteViews bigContentView, boolean sound, boolean vibrate, boolean lights) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        Notification notification=builder.build();
        //在api大于等于16的情况下，如果视图超过一定范围，可以转变成bigContentView
        notification.bigContentView=bigContentView;
        notification.contentView=contentView;
        send(notification);
    }

    /**
     * 列表型通知
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param conntents
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendListNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, ArrayList<String> conntents, boolean sound, boolean vibrate, boolean lights) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            Toast.makeText(context, "您的手机低于Android 4.1.2，不支持！！", Toast.LENGTH_SHORT).show();
            return;
        }
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        NotificationCompat.InboxStyle style=new NotificationCompat.InboxStyle(builder);
        for (String conntent : conntents) {
            style.addLine(conntent);
        }
        style.setSummaryText(conntents.size()+"条消息");
        style.setBigContentTitle(title);
        Notification notification=style.build();
        send(notification);
    }

    /**
     * 双折叠双按钮通知
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param leftIcon
     * @param leftText
     * @param leftPI
     * @param rightIcon
     * @param rightText
     * @param rightPI
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendActionNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent,
                                       int leftIcon, String leftText, PendingIntent leftPI,
                                       int rightIcon, String rightText, PendingIntent rightPI,
                                       boolean sound, boolean vibrate, boolean lights) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        builder.addAction(leftIcon, leftText, leftPI);
        builder.addAction(rightIcon, rightText, rightPI);
        Notification notification=builder.build();
        send(notification);
    }

    /**
     * 带进度条的通知栏
     * @param ticker
     * @param title
     * @param content
     * @param smallIcon
     * @param intent
     * @param sound
     * @param vibrate
     * @param lights
     */
    public void sendProgressNotification(String ticker, String title, String content, int smallIcon, PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        sample(ticker, title, content, smallIcon, intent, sound, vibrate, lights);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=100;i+=10) {
                    builder.setProgress(100, i, false);
                    send(builder.build());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //下载完成
                builder.setContentText("下载完成").setProgress(0, 0, false);
                send(builder.build());
            }
        }).start();
    }

    private void send(Notification notification) {
        manager.notify(notificationId, notification);
    }

    public static boolean isDarkNotificationTheme(Context context) {
        return !isSimilarColor(Color.BLACK, getNotificationColor(context));
    }

    /**
     * 获取通知栏颜色
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        Notification notification=builder.build();
        int layoutId=notification.contentView.getLayoutId();
        ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title)!=null) {
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color=Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups=new LinkedList<>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size()>0) {
            ViewGroup viewGroup1=viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                }
                else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor()!=-1) {
                        color=((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }

    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor=baseColor|0xff000000;
        int simpleColor=color|0xff000000;
        int baseRed=Color.red(simpleBaseColor)-Color.red(simpleColor);
        int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
        int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
        double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
        if (value<180.0) {
            return true;
        }
        return false;
    }
}
