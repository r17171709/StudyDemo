package com.example.rg.drawabledemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by RG on 2015/3/16.
 */
public class TabFragment extends Fragment {

    LinearLayout fragment_layout=null;

    int color=0;

    public static TabFragment getInstance(int color) {
        TabFragment fragment=new TabFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("color", color);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        color=getArguments().getInt("color");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_view, container, false);
        fragment_layout=(LinearLayout) view.findViewById(R.id.fragment_layout);
        fragment_layout.setBackgroundColor(color);
        return view;
    }
}
