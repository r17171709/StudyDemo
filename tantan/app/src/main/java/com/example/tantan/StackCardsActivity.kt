package com.example.tantan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tantan.adapter.CardAdapter
import com.example.tantan.view.StackCardsView
import kotlinx.android.synthetic.main.activity_stackcards.*

class StackCardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stackcards)

        val arrayList = ArrayList<String>()
        for (i in 0..10) {
            arrayList.add(""+i)
        }

        val adapter = CardAdapter(this, arrayList)
        view_stackcard.listener = object : StackCardsView.OnCardSwipedListener {
            override fun onCardDismiss() {
                // 删除可移动视图
                adapter.remove(0)
                // 数量不够的时候添加
                if (adapter.getCount() < 4) {
                    for (i in 0..10) {
                        arrayList.add(""+i)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
        view_stackcard.setCardAdapter(adapter)
    }
}