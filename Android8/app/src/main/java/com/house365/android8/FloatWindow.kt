package com.house365.android8

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.*

/**
 * Created by renyu on 2018/1/31.
 */
class FloatWindow(context: Context) : View.OnTouchListener {

    var lastX = 0
    var lastY = 0
    var sourceX = 0
    var sourceY = 0
    var mFloatLayout: View? = null

    val mWindowParams by lazy {
        WindowManager.LayoutParams()
    }
    var mWindowManager: WindowManager? = null

    init {
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?

        initFloatWindow(context)
    }

    private fun initFloatWindow(context: Context) {
        mFloatLayout = LayoutInflater.from(context).inflate(R.layout.view_floatwindow, null, false)
        mFloatLayout?.setOnTouchListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        else {
            mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
        mWindowParams.alpha = 80f
        mWindowParams.format = PixelFormat.RGBX_8888
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        mWindowParams.gravity = Gravity.START or Gravity.TOP
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    fun showFloatWindow() {
        if (mFloatLayout?.parent == null) {
            mWindowParams.x = 0
            mWindowParams.y= 0
            mWindowManager?.addView(mFloatLayout, mWindowParams)
        }
    }

    fun hideFloatWindow() {
        if (mFloatLayout?.parent != null) {
            mWindowManager?.removeView(mFloatLayout)
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null && mFloatLayout != null) {
            var currentX = event.rawX.toInt()
            var currentY = event.rawY.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = currentX
                    lastY = currentY

                    sourceX = currentX
                    sourceY = currentY
                }
                MotionEvent.ACTION_MOVE -> {
                    val extraX = currentX - lastX
                    val extraY = currentY - lastY
                    mWindowParams.x = sourceX + extraX
                    mWindowParams.y = sourceY + extraY
                    mWindowManager?.updateViewLayout(mFloatLayout, mWindowParams)
                }
            }
        }

        return true
    }
}