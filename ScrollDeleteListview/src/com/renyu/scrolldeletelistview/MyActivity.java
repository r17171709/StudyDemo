package com.renyu.scrolldeletelistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.renyu.scrolldeletelistview.myview.MyAdapter;

import java.util.ArrayList;


public class MyActivity extends ActionBarActivity {

    ListView listview=null;
    MyAdapter adapter=null;
    ArrayList<String> lists=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        lists=new ArrayList<String>();
        for (int i=0;i<20;i++) {
            lists.add(""+i);
        }

        adapter=new MyAdapter(MyActivity.this, lists);
        listview=(ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MyActivity.this, "click" + i, Toast.LENGTH_SHORT).show();
            }
        });
        listview.setAdapter(adapter);
    }

}
