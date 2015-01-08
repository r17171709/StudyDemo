package com.renyu.observerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //自己根据观察者设计模式去实现
        Teacher t=new Teacher();
        for (int i=0;i<10;i++) {
            Student s=new Student(t);
            t.attach(s);
        }
        t.setPhoneNum("15895886064");

        //套用java中原有的观察者相关使用类
        Teacher1 t1=new Teacher1();
        for (int i=0;i<10;i++) {
            Student1 s=new Student1();
            t1.addObserver(s);
        }
        t1.setPhoneNum("15295560842");
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
