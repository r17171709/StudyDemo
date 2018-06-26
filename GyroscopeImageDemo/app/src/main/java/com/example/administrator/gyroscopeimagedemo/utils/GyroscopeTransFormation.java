package com.example.administrator.gyroscopeimagedemo.utils;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class GyroscopeTransFormation implements Transformation {

    private int mWidgetWidth;   //控件宽度
    private int mWidgetHeight;  //控件高度
    private double mTargetWidth;    //目标宽度
    private double mTargetHeight;   //目标高度

    public GyroscopeTransFormation(int widgetWidth, int widgetHeight) {
        mWidgetWidth = widgetWidth;
        mWidgetHeight = widgetHeight;
    }

    @Override public Bitmap transform(Bitmap source) {

        if (source.getWidth() == 0 || source.getHeight() == 0) {
            return source;
        }

        mTargetWidth = source.getWidth();
        mTargetHeight = source.getHeight();

        double ratio = mTargetWidth / mTargetHeight;     //图片的宽高比
        int distance;                                    //图片缩放后与控件边的距离

        if (mWidgetHeight <= mWidgetWidth) {
            distance = mWidgetHeight / 8;
            mTargetWidth = mWidgetWidth + 2 * distance;
            mTargetHeight = mTargetWidth / ratio;
        } else {
            distance = mWidgetWidth / 8;
            mTargetHeight = mWidgetHeight + 2 * distance;
            mTargetWidth = mTargetHeight * ratio;
        }

        int desiredWidth = (int) mTargetWidth;
        int desiredHeight = (int) mTargetHeight;

        Bitmap result = Bitmap.createScaledBitmap(source, desiredWidth, desiredHeight, false);

        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }
        return result;
    }

    @Override public String key() {
        return "";
    }
}
