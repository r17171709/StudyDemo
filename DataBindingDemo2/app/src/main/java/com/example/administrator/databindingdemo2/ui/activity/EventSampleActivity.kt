package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityEventsampleBinding
import com.example.administrator.databindingdemo2.impl.ClickEventImpl
import com.example.administrator.databindingdemo2.util.MyHandlers

class EventSampleActivity : AppCompatActivity(), ClickEventImpl {
    override fun clickEvent(view: View, string: String) {
        Toast.makeText(view.context, "EventSampleActivity : $string", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityEventsampleBinding>(this, R.layout.activity_eventsample)
        viewDataBinding.handlers = MyHandlers()
        viewDataBinding.impl = this
    }
}