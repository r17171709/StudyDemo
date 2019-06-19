package com.house365.activitybuilderdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.house365.annotation.annotation.BuilderActivity;
import com.house365.annotation.annotation.BuilderField;

import java.util.ArrayList;

@BuilderActivity(routerValue = "SecondActivity")
public class SecondActivity extends AppCompatActivity {
    @BuilderField
    RequestBeans beans;
    @BuilderField
    ArrayList<String> beans2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beans = (RequestBeans) getIntent().getSerializableExtra("beans");
        beans2 = getIntent().getStringArrayListExtra("beans2");
    }
}
