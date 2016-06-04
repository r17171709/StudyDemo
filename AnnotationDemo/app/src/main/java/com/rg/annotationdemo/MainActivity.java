package com.rg.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.annotation.ButterKnife;
import com.example.annotation.ViewInject;

@ViewInject(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife butterKnife=new ButterKnife();
        butterKnife.bind(this);
    }

    public void click() {
        Toast.makeText(this, "HHH", Toast.LENGTH_SHORT).show();
    }
}
