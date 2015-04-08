package com.example.rg.androidstudiodemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rg.androidstudiodemo.jni.MathKit;
import com.example.rg.mylibrary.HelloModule;
import com.linkage.gasstationjni.GasJni;

import net.sourceforge.pinyin4j.PinyinHelper;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用android studio去执行NDK编译
        System.out.println(MathKit.square(10));
        //使用jar
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(test());
            }
        }).start();
        //module调用
        HelloModule helloModule=new HelloModule();
        System.out.println(helloModule.getModuleValue());
        //使用已经存在的so
        GasJni jni=new GasJni();
        System.out.println(jni.stringFromJNI(System.currentTimeMillis(), "123", "123", "123", "123123"));
    }

    public String test() {
        String str="";
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('单');
        for(int i = 0; i < pinyinArray.length; ++i) {
            str+=pinyinArray[i];
        }
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
