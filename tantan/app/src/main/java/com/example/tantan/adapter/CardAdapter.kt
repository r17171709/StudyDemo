package com.example.tantan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tantan.R
import com.example.tantan.view.StackCardsView

class CardAdapter(private val context: Context, private val list: ArrayList<String>) : StackCardsView.Adapter() {

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false)
        view.setOnClickListener {
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show()
        }
        return view
    }

    fun remove(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}