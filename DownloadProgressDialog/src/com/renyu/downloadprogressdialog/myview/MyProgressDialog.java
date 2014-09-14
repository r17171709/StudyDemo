package com.renyu.downloadprogressdialog.myview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2014/9/14.
 */
public class MyProgressDialog extends ProgressDialog {

    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	showProgress(View.INVISIBLE);
    }

    public void showProgress(int visibility) {
        try {
            Method setVisibility= TextView.class.getMethod("setVisibility", Integer.TYPE);

            //mProgressNumber对象在ProgressDialog中为私有类型，得到此对象之后，方可执行setVisibility方法
            Field mProgressNumber_field=this.getClass().getSuperclass().getDeclaredField("mProgressNumber");
            mProgressNumber_field.setAccessible(true);
            TextView mProgressNumber=(TextView) mProgressNumber_field.get(this);
            setVisibility.invoke(mProgressNumber, visibility);

            //
            Field mProgressPercent_field=this.getClass().getSuperclass().getDeclaredField("mProgressPercent");
            mProgressPercent_field.setAccessible(true);
            TextView mProgressPercent=(TextView) mProgressPercent_field.get(this);
            setVisibility.invoke(mProgressPercent, visibility);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
