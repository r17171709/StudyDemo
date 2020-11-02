package com.rg.annotationdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.PrintInject;

@PrintInject(value = {1, 2})
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
