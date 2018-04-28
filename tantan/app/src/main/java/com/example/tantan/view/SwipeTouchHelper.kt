package com.example.tantan.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.example.tantan.utils.ViewDragHelperUtils
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringConfig
import com.facebook.rebound.SpringSystem


class SwipeTouchHelper private constructor() {

    private val SLOPE = 1.732f

    // 是否发送拦截
    private var mIsBeingDragged = false
    // 是否发送触摸
    private var mIsTouchOn = false
    // 首次触摸坐标
    private var mInitDownX = 0f
    private var mInitDownY = 0f
    // 上一次触摸的坐标
    private var mLastX = 0f
    private var mLastY = 0f
    // 当前可移动视图移动比例
    private var mCurProgress = 0f

    // rebound动画开始执行坐标及旋转角度
    var mAnimStartX = 0f
    var mAnimStartY = 0f
    var mAnimStartRotation = 0f

    companion object {
        @Volatile
        private var instance: SwipeTouchHelper? = null

        // 初次加载标志
        private var mInitPropSetted = false

        private var stackCardsView: StackCardsView? = null
        // 当前可拖动的View
        private var mTouchChild: View? = null
        // 可拖动View的初始位置
        var mChildInitX = 0f
        var mChildInitY = 0f

        // 基本滑动参数
        private var mDragSlop: Int? = null
        private var mMaxVelocity: Int? = null
        private var mMinVelocity: Int? = null

        // Debound参数配置
        private var springSystem: SpringSystem? = null
        private var spring: Spring? = null

        fun getInstance(view: StackCardsView) : SwipeTouchHelper {
            if (instance == null) {
                synchronized(SwipeTouchHelper::class) {
                    if (instance == null) {
                        instance = SwipeTouchHelper()

                        stackCardsView = view

                        val configuration = ViewConfiguration.get(view.context)
                        mDragSlop = configuration.scaledTouchSlop
                        mMaxVelocity = configuration.scaledMaximumFlingVelocity
                        mMinVelocity = configuration.scaledMinimumFlingVelocity

                        springSystem = SpringSystem.create()

                        updateTouchChild()
                    }
                }
            }
            return instance!!
        }

        /**
         * 重新寻找当前可拖动的View，特别是在ViewGroup整体刷新的时候原先的mTouchChild已经不在该ViewGroup上了
         */
        private fun updateTouchChild() {
            val index = stackCardsView!!.indexOfChild(mTouchChild)
            // 将下一个View作为mTouchChild
            val next = index +1
            mTouchChild = if (next < stackCardsView!!.childCount) stackCardsView!!.getChildAt(next) else null
            if (mTouchChild != null) {
                if (!mInitPropSetted) {
                    mInitPropSetted = true
                    // 可拖动View的起始位置不会发生变化，所以只需要获取一次就行
                    mChildInitX = mTouchChild!!.x
                    mChildInitY = mTouchChild!!.y
                }
            }
        }
    }

    fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val x = ev!!.x
        val y = ev.y
        if (mTouchChild == null) {
            return false
        }
        if (mIsBeingDragged && ev.action != MotionEvent.ACTION_DOWN) {
            return true
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!ViewDragHelperUtils.isTouchOnView(mTouchChild, x, y)) {
                    return false
                }
                mIsTouchOn = true
                mInitDownX  = x
                mInitDownY  = y
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (!mIsBeingDragged) {
                    // 判断滑动距离是否允许拦截
                    if (Math.abs(x- mInitDownX) < mDragSlop!! && Math.abs(y-mInitDownY)< mDragSlop!!) {
                        mLastX = x
                        mLastY = y
                        return mIsBeingDragged
                    }
                    mIsBeingDragged = true
                }
                mLastX = x
                mLastY = y
            }
        }
        return mIsBeingDragged
    }

    fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y
        if (mTouchChild == null) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!ViewDragHelperUtils.isTouchOnView(mTouchChild, x, y)) {
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!mIsBeingDragged) {
                    // 判断滑动距离是否允许拦截
                    if (Math.abs(x- mInitDownX) < mDragSlop!! && Math.abs(y-mInitDownY)< mDragSlop!!) {
                        mLastX = x
                        mLastY = y
                        return true
                    }
                    mIsBeingDragged = true
                }
                performDrag(x - mLastX, y - mLastY)
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                doAnim()
            }
        }
        return true
    }

    private fun resetTouch() {
        mIsTouchOn = false
        mIsBeingDragged = false
    }

    /**
     * 手指松开后，判断下一步是重置还是消失
     */
    private fun doAnim() {
        if (isDistanceAllowDismiss()) {
            doSlowDisappear()
        }
        else {
            animateToInitPos()
        }
        resetTouch()
    }

    /**
     * 绘制当前可移动视图
     */
    private fun performDrag(dx: Float, dy: Float) {
        mTouchChild!!.x = mTouchChild!!.x + dx
        mTouchChild!!.y = mTouchChild!!.y + dy
        // 用X轴数值作为进度计算体验度比较好
        val progress = (mTouchChild!!.x - mChildInitX) / stackCardsView!!.getDismissDistance()
        // 控制进度在一定范围内
        var rotation = 8 * progress
        if (rotation > 8) {
            rotation = 8f
        } else if (rotation < -8) {
            rotation = -8f
        }
        mTouchChild!!.rotation = rotation
        // 将剩下未滑动的视图进行动画调整
        onCoverScrolled(mTouchChild!!)
    }

    /**
     * 根据可移动视图移动比例，修改其他视图状态
     */
    private fun onCoverScrolled(movingView: View) {
        // 获取拖动比例，修改其他视图状态
        val selfInfo = calcScrollInfo(movingView)
        mCurProgress = selfInfo.progress
        stackCardsView!!.updateChildrenProgress(selfInfo.progress, movingView)
    }

    /**
     * 获取当前可移动视图移动比例消息
     */
    private fun calcScrollInfo(movingView: View) : ScrollInfo {
        val x = movingView.x - mChildInitX
        val y = movingView.y - mChildInitY
        val distance = Math.sqrt((x*x+y*y).toDouble())
        return if (distance> stackCardsView!!.getDismissDistance()) {
            ScrollInfo(1f)
        }
        else {
            ScrollInfo(distance.toFloat() / stackCardsView!!.getDismissDistance())
        }
    }

    /**
     * 判断当前可移动视图是否达到可消失情况
     */
    private fun isDistanceAllowDismiss() : Boolean {
        val x = mTouchChild!!.x - mChildInitX
        val y = mTouchChild!!.y - mChildInitY
        val distance = Math.sqrt((x*x+y*y).toDouble())
        return distance> stackCardsView!!.getDismissDistance()
    }

    private fun animateToInitPos() {
        if (spring != null) {
            spring!!.removeAllListeners()
        }

        mAnimStartX = mTouchChild!!.x
        mAnimStartY = mTouchChild!!.y
        mAnimStartRotation = mTouchChild!!.rotation

        spring = springSystem!!.createSpring()
        spring!!.springConfig = SpringConfig.fromOrigamiTensionAndFriction(40.0, 5.0)
        spring!!.addListener(object : SimpleSpringListener() {
            override fun onSpringUpdate(spring: Spring?) {
                super.onSpringUpdate(spring)
                val process = spring!!.currentValue
                mTouchChild!!.x = (mAnimStartX + (mChildInitX - mAnimStartX) * process).toFloat()
                mTouchChild!!.y = (mAnimStartY + (mChildInitY - mAnimStartY) * process).toFloat()
                mTouchChild!!.rotation = (mAnimStartRotation + (0 - mAnimStartRotation) * process).toFloat()
                // 将剩下未滑动的视图进行动画调整
                onCoverScrolled(mTouchChild!!)
            }
        })
        spring!!.endValue = 1.0
    }

    private fun doSlowDisappear() {
        val disappearView = mTouchChild
        val initX = mChildInitX
        val initY = mChildInitY
        // 添加新子View，重新获取新的可触摸视图
        stackCardsView!!.tryAppendChild()
        updateTouchChild()
        val currentX = disappearView!!.x
        val currentY = disappearView.y
        val dx = currentX - initX
        val dy = currentY - initY
        val rect = Rect()
        disappearView.getHitRect(rect)
        // 动画所需属性
        var property: String
        var target: Float
        var duration: Long
        var delta: Float
        if (Math.abs(dx) * SLOPE > Math.abs(dy)) {
            val width = stackCardsView!!.width
            property = "x"
            delta = if (dx>0) {
                (width - rect.left).toFloat()
            } else {
                - rect.right.toFloat()
            }
            target = currentX + delta
            duration = ViewDragHelperUtils.computeSettleDuration(stackCardsView, delta.toInt(), 0, 0, 0, mMinVelocity!!, mMaxVelocity!!).toLong()
        }
        else {
            val height = stackCardsView!!.height
            property = "y"
            delta = if (dy > 0) {
                (height - rect.top).toFloat()
            } else {
                - rect.bottom.toFloat()
            }
            target = currentY + delta
            duration = ViewDragHelperUtils.computeSettleDuration(stackCardsView, 0, delta.toInt(), 0, 0, mMinVelocity!!, mMaxVelocity!!).toLong()
        }
        val animation: ObjectAnimator = ObjectAnimator.ofFloat(disappearView, property, target).setDuration(duration)
        animation.interpolator = ViewDragHelperUtils.sInterpolator
        animation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                // 删除可移动视图
                stackCardsView!!.removeCover()
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
            }
        })
        animation.start()
    }

    /**
     * 数据刷新之后重置mTouchChild
     */
    fun onChildChanged() {
        mTouchChild = null
        updateTouchChild()
    }
}

data class ScrollInfo(var progress: Float)