package com.renyu.ninegridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.renyu.ninegridview.adapter.MainAdapter;
import com.renyu.ninegridview.model.NineGridImageModel;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView nine_lv;
    ArrayList<ArrayList<NineGridImageModel>> lists;

    String[] images=new String[]{"http://d.lanrentuku.com/down/png/1510/weixin-qq-icon/weixin.png",
            "http://d.lanrentuku.com/down/png/1510/weixin-qq-icon/pengyouquan.png",
            "http://d.lanrentuku.com/down/png/1510/weixin-qq-icon/qq.png",
            "http://d.lanrentuku.com/down/png/1510/weixin-qq-icon/qqcom.png",
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
            int random=new Random().nextInt(7);
            ArrayList<NineGridImageModel> temps=new ArrayList<>();
            for (int j=0;j<random;j++) {
                NineGridImageModel model=new NineGridImageModel();
                model.setImage(images[new Random().nextInt(images.length)]);
                temps.add(model);
            }
            Log.d("MainActivity", "temps.size():" + temps.size());
            lists.add(temps);
        }
        nine_lv= (ListView) findViewById(R.id.nine_lv);
        nine_lv.setAdapter(new MainAdapter(this, lists));
    }
}
