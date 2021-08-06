package com.example.administrator.gyroscopeimagedemo.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout

class TouchRotateLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val mCamera by lazy { Camera() }
    private val mMatrix by lazy { Matrix() }
    private var recoverAnimator: ValueAnimator? = null

    private var lastX = 0f
    private var lastY = 0f

    // 旋转角度
    private var mRotateX = 0f
    private var mRotateY = 0f

    // 复原时的起始位置
    private var mUpRotateX = 0f
    private var mUpRotateY = 0f

    init {
        recoverAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 500
            interpolator = BounceInterpolator()
            addUpdateListener {
                val percent = it.animatedValue as Float
                mRotateX = mUpRotateX * percent
                mRotateY = mUpRotateY * percent

                postInvalidate()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (recoverAnimator != null) {
                    recoverAnimator!!.cancel()
                }
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                calcRotateXY(event.x - lastX, event.y - lastY, false)
            }
            MotionEvent.ACTION_UP -> {
                calcRotateXY(event.x - lastX, event.y - lastY, true)
                recoverAnimator?.start()
            }
        }
        return true
    }

    private fun calcRotateXY(x: Float, y: Float, isUp: Boolean) {
        val percentX = x / (width / 2)
        mRotateX = percentX * 30

        val percentY = y / (height / 2)
        mRotateY = percentY * 30

        if (isUp) {
            mUpRotateX = mRotateX
            mUpRotateY = mRotateY
        }

        postInvalidate()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        // 重置Matrix
        mMatrix.reset()

        canvas?.save()
        mCamera.save()

        mCamera.rotateX(mRotateY)
        mCamera.rotateY(mRotateX)

        mCamera.getMatrix(mMatrix)

        // 将原点移动到中心位置
        mMatrix.preTranslate(-width / 2f, -height / 2f)
        mMatrix.postTranslate(width / 2f, height / 2f)

        // 操作完并且获取了Matrix后要恢复camera的状态
        mCamera.restore()

        // 将操作camera产生的Matrix赋给canvas
        canvas?.setMatrix(mMatrix)

        // 真正绘制
        super.dispatchDraw(canvas)

        // 绘制完之后要恢复canvas的状态
        canvas?.restore()
    }
}