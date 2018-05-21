package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.renyu.databindingdemo.adapter.RecyclerViewAdapter
import com.renyu.databindingdemo.databinding.ActivityRecyclerviewBinding
import com.renyu.databindingdemo.model.User
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerViewActivity : AppCompatActivity() {

    var adapter: RecyclerViewAdapter? = null

    private val beans: ArrayList<User> by lazy {
        ArrayList<User>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBindingUtil = DataBindingUtil.setContentView<ActivityRecyclerviewBinding>(this, R.layout.activity_recyclerview)

        for (i in 0..39) {
            val user = User()
            user.id = "$i"
            user.name = "renyu$i"
            user.blog = "https://github.com/r17171709/android_demo"
            beans.add(user)
        }

        dataBindingUtil.adapter = RecyclerViewAdapter(beans)
    }
}