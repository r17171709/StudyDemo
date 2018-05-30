package com.renyu.aocdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.renyu.aocdemo.databinding.ActivityMainBinding;
import com.renyu.aocdemo.model.UserModel;
import com.renyu.aocdemo.singleclick.SingleClick;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements EventListener {

    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.init(getApplicationContext());

        super.onCreate(savedInstanceState);
        ActivityMainBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewDataBinding.setListener(this);

        userModel = new UserModel();
        userModel.setName("HI");
    }

    @Override
    public void onViewClick(@NotNull View view) {
//        clickTest(view);
//        abc();
//        Toast.makeText(this, userModel.getName(), Toast.LENGTH_SHORT).show();

        hisay();
    }

    @SingleClick(exceptViews = {R.id.tv_main})
    public void clickTest(View view) {
        System.out.println("clickTest");
    }

    public void check(String string) {
        System.out.println("AOP测试");
    }

//    public String sayHi() {
//        Log.d("CheckNetworkAspectJ", "HI AOP");
//        return "HI AOP";
//    }

//    public String abc() {
//        System.out.println("abc");
//        sayHi();
//        return "abc";
//    }

//    public void sayHi() {
//        System.out.println("HI AOP");
//    }

    public void hisay() {
        System.out.println("HI AOP");
    }
}
