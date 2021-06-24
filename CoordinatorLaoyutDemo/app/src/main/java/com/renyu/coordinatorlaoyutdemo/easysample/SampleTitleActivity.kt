package com.renyu.coordinatorlaoyutdemo.easysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renyu.coordinatorlaoyutdemo.R
import com.renyu.coordinatorlaoyutdemo.adapter.VideoListAdapter

class SampleTitleActivity : AppCompatActivity() {
    private var rv_sampletitle: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sampletitle)

        rv_sampletitle = findViewById(R.id.rv_sampletitle)
        val layoutManager = LinearLayoutManager(this)
        rv_sampletitle?.layoutManager = layoutManager
        rv_sampletitle?.setHasFixedSize(true)
        rv_sampletitle?.adapter = VideoListAdapter()
    }
}