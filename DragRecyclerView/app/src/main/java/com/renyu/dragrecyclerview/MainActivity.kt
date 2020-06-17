package com.renyu.dragrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.renyu.dragrecyclerview.adapter.RecyclerViewAdapter
import com.renyu.dragrecyclerview.utils.CallbackItemTouch
import com.renyu.dragrecyclerview.utils.MyItemTouchHelperCallBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val beans by lazy {
        ArrayList<String>()
    }
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.setHasFixedSize(true)
        beans.apply {
            for (i in 0 until 20) {
                add("$i")
            }
        }
        val adapter = RecyclerViewAdapter(beans)
        rv.adapter = adapter
        val itemTouchHelperCallBack = MyItemTouchHelperCallBack(object : CallbackItemTouch {
            override fun onMove(oldPosition: Int, newPosition: Int) {
                beans.add(newPosition, beans.removeAt(oldPosition))
                adapter.notifyItemMoved(oldPosition, newPosition)
            }
        })
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper!!.attachToRecyclerView(rv)
    }

    fun startDrag(vh: RecyclerView.ViewHolder) {
        itemTouchHelper!!.startDrag(vh)
    }
}
