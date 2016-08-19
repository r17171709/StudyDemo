package com.renyu.ninegridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.renyu.ninegridview.adapter.MainAdapter;
import com.renyu.ninegridview.adapter.RVAdapter;
import com.renyu.ninegridview.model.NineGridImageModel;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView nine_lv;
    ArrayList<ArrayList<NineGridImageModel>> lists;

    RecyclerView nine_rv;

    String[] images=new String[]{
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/portugal-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/usa-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/spain-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/chile-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/brazil-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/Japan-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/england-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/germany-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/argentina-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/korea-republic-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/italy-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/belgium-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/netherlands-flag-icon.png",
            "http://d.lanrentuku.com/down/png/1406/fifa_world_cup_2014_teams_country_flags/france-flag-icon.png"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lists=new ArrayList<>();
        for (int i=0;i<15;i++) {
            int random=new Random().nextInt(8);
            ArrayList<NineGridImageModel> temps=new ArrayList<>();
            boolean isOne=false;
            if (random==1) {
                isOne=true;
            }
            for (int j=0;j<random;j++) {
                NineGridImageModel model=new NineGridImageModel();
                if (isOne) {
                    model.setHeight(400);
                    model.setWidth(700);
                }
                model.setImage(images[new Random().nextInt(images.length)]);
                temps.add(model);
            }
            Log.d("MainActivity", "temps.size():" + temps.size());
            lists.add(temps);
        }
//        nine_lv= (ListView) findViewById(R.id.nine_lv);
//        nine_lv.setAdapter(new MainAdapter(this, lists));

        nine_rv= (RecyclerView) findViewById(R.id.nine_rv);
        nine_rv.setHasFixedSize(true);
        nine_rv.setLayoutManager(new LinearLayoutManager(this));
        nine_rv.setAdapter(new RVAdapter(lists, this));
    }
}
