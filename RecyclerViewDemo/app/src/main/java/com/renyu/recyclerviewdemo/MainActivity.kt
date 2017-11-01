package com.renyu.recyclerviewdemo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.renyu.recyclerviewdemo.view.Divider
import com.renyu.recyclerviewdemo.view.GridDivider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: MainAdapter? = null

    val beans: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (0 until 31).mapTo(beans) { "item  $it" }

        rv_demo.hasFixedSize()

//        rv_demo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        rv_demo.addItemDecoration(Divider(this, ContextCompat.getDrawable(this, R.drawable.item_divider), LinearLayoutManager.HORIZONTAL))

        rv_demo.layoutManager = GridLayoutManager(this, 5)
        rv_demo.addItemDecoration(GridDivider(this,  ContextCompat.getDrawable(this, R.drawable.item_divider)))
        adapter = MainAdapter(this, beans)
        rv_demo.adapter = adapter

    }
}
