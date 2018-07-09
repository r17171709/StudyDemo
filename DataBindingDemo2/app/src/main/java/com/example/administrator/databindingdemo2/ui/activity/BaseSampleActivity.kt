package com.example.administrator.databindingdemo2.ui.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.administrator.databindingdemo2.R
import com.example.administrator.databindingdemo2.databinding.ActivityBasesampleBinding
import com.example.administrator.databindingdemo2.model.Course
import com.example.administrator.databindingdemo2.model.Student
import com.example.administrator.databindingdemo2.model.Teacher

class BaseSampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewDataBinding = DataBindingUtil.setContentView<ActivityBasesampleBinding>(this, R.layout.activity_basesample)
        viewDataBinding.teacher = Teacher("renyu", 30)
        viewDataBinding.student = Student("PQ", 28)
        val maps = HashMap<String, Teacher>()
        maps["renyu"] = Teacher("renyu", 30)
        val course = Course(maps)
        viewDataBinding.courses = course

//        Handler().postDelayed({
//            viewDataBinding.teacher?.age = 31
//        }, 5000)
    }
}