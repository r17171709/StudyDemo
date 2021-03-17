package com.rg.annotationdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rg.annotationdemo.annotation.EventInject;
import com.rg.annotationdemo.annotation.OnLongClick;

public class AnnotationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventInject.inject(this);
    }

    @OnLongClick(R.id.textview)
    public boolean abc(View view) {
        Toast.makeText(this, "Hello " + view.getClass(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
