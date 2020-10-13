package com.renyu.viewpager2demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_color.*

class ColorFragment : Fragment {
    private var color: Int = 0

    constructor()

    constructor(color: Int) {
        this.color = color
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_color, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_bg.setBackgroundColor(color)
        delete.setOnClickListener {
            (activity as MainActivity).adapter.removeFragment(this)
        }
    }
}