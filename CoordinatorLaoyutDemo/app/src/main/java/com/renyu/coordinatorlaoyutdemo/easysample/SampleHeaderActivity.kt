package com.renyu.coordinatorlaoyutdemo.easysample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renyu.coordinatorlaoyutdemo.R
import com.renyu.coordinatorlaoyutdemo.adapter.VideoListAdapter

class SampleHeaderActivity : AppCompatActivity() {
    private var rv_samplehead: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sampleheader)

        rv_samplehead = findViewById(R.id.rv_samplehead)
        val layoutManager = LinearLayoutManager(this)
        rv_samplehead?.layoutManager = layoutManager
        rv_samplehead?.setHasFixedSize(true)
        rv_samplehead?.adapter = VideoListAdapter()
    }
}