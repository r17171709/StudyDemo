package com.renyu.databindingdemo

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.renyu.databindingdemo.adapter.RecyclerView2Adapter
import com.renyu.databindingdemo.model.User
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerView2Activity : AppCompatActivity() {

    var adapter: RecyclerView2Adapter<in ViewDataBinding, in MulTypeImpl>? = null

    private val beans: ArrayList<MulTypeImpl> by lazy {
        ArrayList<MulTypeImpl>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        for (i in 0..39) {
            val user = User()
            user.id = "$i"
            user.name = "renyu$i"
            user.blog = "https://github.com/r17171709/android_demo"
            beans.add(user)
        }

        adapter = RecyclerView2Adapter(beans, RVListener())
        rv_rv.layoutManager = LinearLayoutManager(this)
        rv_rv.setHasFixedSize(true)
        rv_rv.adapter = adapter
    }

    inner class RVListener {
        fun onClick(user: User) {
            toast("点击了"+user.name)
        }
    }
}
