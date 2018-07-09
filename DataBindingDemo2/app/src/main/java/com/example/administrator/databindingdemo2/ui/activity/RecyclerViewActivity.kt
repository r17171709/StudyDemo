package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityRecyclerviewBinding
import com.example.administrator.databindingdemo2.model.Teacher2
import com.example.administrator.databindingdemo2.ui.adapter.RecyclerViewAdapter

class RecyclerViewActivity : AppCompatActivity() {

    val teacher2: ArrayList<Teacher2> by lazy {
        ArrayList<Teacher2>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityRecyclerviewBinding>(this, R.layout.activity_recyclerview)
        viewDataBinding.adapter = RecyclerViewAdapter(teacher2)

        for (i in 0..30) {
            teacher2.add(Teacher2(ObservableField("Hello$i"), ObservableField(i)))
        }
        viewDataBinding.adapter?.notifyDataSetChanged()
    }
}