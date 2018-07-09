package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityEdittextBinding
import com.example.administrator.databindingdemo2.model.ShowHide
import com.example.administrator.databindingdemo2.model.Teacher2
import kotlinx.android.synthetic.main.activity_edittext.*

class EditTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityEdittextBinding>(this, R.layout.activity_edittext)
        viewDataBinding.teacher2 = Teacher2(ObservableField(""), ObservableField(0))
        viewDataBinding.showhide = ShowHide(ObservableBoolean(true))

        Handler().postDelayed({
//            viewDataBinding.showhide?.sh?.set(false)

            view_inversebinding.visibility = View.GONE
            val value = viewDataBinding.showhide?.sh?.get()
            Toast.makeText(this@EditTextActivity, "参考值为$value", Toast.LENGTH_LONG).show()
        }, 5000)
    }
}