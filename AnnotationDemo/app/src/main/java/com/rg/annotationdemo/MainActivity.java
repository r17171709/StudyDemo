package com.rg.annotationdemo;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.annotation.ViewInject;

//@PrintInject(value = {1, 2})
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.textview)
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.rg.annotationdemo.MainActivity$ViewBinding binding = new com.rg.annotationdemo.MainActivity$ViewBinding();
        binding.bind(this);

        textview.setOnClickListener(v -> {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        });
    }
}
