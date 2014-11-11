package com.renyu.slidingmenu.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by ry on 2014/11/7.
 */
public class CommonUtils {

    /**
     * »ñÈ¡ÆÁÄ»¿í¸ß
     * @param context
     * @return
     */
    public static int[] getScreenWH(Context context) {
        int[] info=new int[2];
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        info[0]=dm.widthPixels;
        info[1]=dm.heightPixels;
        return info;
    }
}
