package com.renyu.recyclerviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.renyu.recyclerviewdemo.R
import com.renyu.recyclerviewdemo.adapter.DragSwipeAdapter
import com.renyu.recyclerviewdemo.impl.OnDragMoveListener
import com.renyu.recyclerviewdemo.impl.OnSwipeListener
import com.renyu.recyclerviewdemo.impl.StartDragCallBack
import com.renyu.recyclerviewdemo.impl.StartDragAndSwipeListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by renyu on 2017/11/17.
 */
class DragSwipeActivity : AppCompatActivity() {

    private val beans = ArrayList<String>()
    var itemTouchHelper: ItemTouchHelper? = null
    var adapter: DragSwipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (0..30).mapTo(beans) { "$it" }

        var callBack = StartDragCallBack(object : OnDragMoveListener {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) {
                if (viewHolder?.itemViewType == target?.itemViewType) {
                    Collections.swap(beans, viewHolder?.layoutPosition!!, target?.layoutPosition!!)
                    adapter?.notifyItemMoved(viewHolder?.layoutPosition!!, target?.layoutPosition!!)
                }
            }
        }, object : OnSwipeListener {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                beans.removeAt(viewHolder?.layoutPosition!!)
                adapter?.notifyItemRemoved(viewHolder?.layoutPosition!!)
            }
        })
        itemTouchHelper = ItemTouchHelper(callBack)
        adapter = DragSwipeAdapter(beans, this, object : StartDragAndSwipeListener {
            override fun onStartSwipe(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper?.startSwipe(viewHolder)
            }

            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper?.startDrag(viewHolder)
            }
        })
        rv_demo.layoutManager = LinearLayoutManager(this)
        rv_demo.setHasFixedSize(true)
        rv_demo.adapter = adapter
        itemTouchHelper?.attachToRecyclerView(rv_demo)

    }

}