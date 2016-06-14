package com.example.clevo.heartlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clevo.heartlayout.view.HeartView;

public class MainActivity extends AppCompatActivity {

    HeartView main_heartview;
    Button main_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_heartview= (HeartView) findViewById(R.id.main_heartview);
        main_button= (Button) findViewById(R.id.main_button);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_heartview.addHeart();
            }
        });
    }
}
