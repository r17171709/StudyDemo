package com.example.administrator.livedatademo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.administrator.livedatademo.R
import com.example.administrator.livedatademo.databinding.ActivityMainBinding
import com.example.administrator.livedatademo.model.AutoRefresh
import com.example.administrator.livedatademo.model.Data1
import com.example.administrator.livedatademo.viewmodel.MainViewModel
import com.example.administrator.livedatademo.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private var vm: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewDataBinding.rf = AutoRefresh(ObservableBoolean(false))

        vm = ViewModelProviders.of(this, MainViewModelFactory(viewDataBinding.rf!!)).get(MainViewModel::class.java)
        vm?.responses?.observe(this, Observer<ArrayList<Data1>> {
            Toast.makeText(this, "得到结果", Toast.LENGTH_LONG).show()
            vm?.notifyDataSetChanged(it!!)
        })

        viewDataBinding.adapter = vm?.adapter

        vm?.id?.value = "1"
    }
}
