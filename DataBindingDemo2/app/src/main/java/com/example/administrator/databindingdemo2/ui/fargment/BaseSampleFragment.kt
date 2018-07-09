package com.example.administrator.databindingdemo2.ui.fargment

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.databinding.OnRebindCallback
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityBasesampleBinding
import com.example.administrator.databindingdemo2.model.Teacher

class BaseSampleFragment : Fragment() {

    val teacher: Teacher by lazy {
        Teacher("renyu", 31)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewDataBinding = DataBindingUtil.inflate<ActivityBasesampleBinding>(inflater, R.layout.activity_basesample, container, false)
        viewDataBinding.addOnRebindCallback(object : OnRebindCallback<ActivityBasesampleBinding>() {
            override fun onBound(binding: ActivityBasesampleBinding?) {
                super.onBound(binding)
                Log.d(this@BaseSampleFragment::class.simpleName, "onBound")
            }

            override fun onCanceled(binding: ActivityBasesampleBinding?) {
                super.onCanceled(binding)
                Log.d(this@BaseSampleFragment::class.simpleName, "onCanceled")
            }

            override fun onPreBind(binding: ActivityBasesampleBinding?): Boolean {
                Log.d(this@BaseSampleFragment::class.simpleName, "onPreBind")
                return super.onPreBind(binding)
            }
        })
        viewDataBinding.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.d(this@BaseSampleFragment::class.simpleName, "$propertyId")
            }
        })
        viewDataBinding.teacher = teacher
        return viewDataBinding.root
    }
}