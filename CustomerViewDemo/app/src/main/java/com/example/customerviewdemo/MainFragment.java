package com.example.customerviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MainFragment extends Fragment {

    RecyclerView rv_main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        rv_main=view.findViewById(R.id.rv_main);
        rv_main.setHasFixedSize(true);
        rv_main.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> strings=new ArrayList<>();
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        strings.add("");
        MainAdapter adapter=new MainAdapter(strings, getActivity());
        rv_main.setAdapter(adapter);
        return view;
    }

    public RecyclerView getRv_main() {
        return rv_main;
    }
}
