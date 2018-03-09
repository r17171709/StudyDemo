package com.renyu.keyboarddemo

import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var view_keyboard: KeyboardView? = null

    // 滚动距离
    var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Utils.init(this)

        view_keyboard = findViewById(R.id.view_keyboard)
        ed_main.setKeyboardType(layout_main, view_keyboard!!, true)
        ed_main.listener = object : KeyBoardEditText.OnKeyboardStateChangeListener {
            override fun show() {
                ed_main.post {
                    val rect = IntArray(2)
                    ed_main.getLocationOnScreen(rect)
                    val y = rect[1]

                    view_keyboard?.getLocationOnScreen(rect)
                    val keyboardY = rect[1]

                    height = y-(keyboardY-ed_main.measuredHeight)
                    layout_root.scrollBy(0, height)
                }

            }

            override fun hide() {
                layout_root.scrollBy(0, -height)
            }
        }
    }
}
