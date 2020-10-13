package com.renyu.viewpager2demo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val colors =
        arrayOf(Color.BLACK, Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN)

    val adapter by lazy {
        MainAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vp2.adapter = adapter
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        button.setOnClickListener {
            adapter.createFragment(ColorFragment(colors[Random().nextInt(colors.size)]))
        }

        update.setOnClickListener {
            adapter.totalUpdate()
        }

        TabLayoutMediator(
            tablayout, vp2, true
        ) { tab, position -> tab.text = "$position" }.attach()
    }
}