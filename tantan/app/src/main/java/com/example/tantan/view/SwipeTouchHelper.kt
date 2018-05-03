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


class SwipeTouchHelper constructor(val stackCardsView: StackCardsView) {

    private val SLOPE = 1.732f

    // 初次加载标志
    private var mInitPropSetted = false

    // 当前选中的View
    private var mTouchChild: View? = null
    // 当前选中的View的初始位置
    var mChildInitX = 0f
    var mChildInitY = 0f

    // 基本滑动参数
    private var mTouchSlop: Int? = null
    private var mMaxVelocity: Int? = null
    private var mMinVelocity: Int? = null

    // Debound参数配置
    private var springSystem: SpringSystem? = null
    private var spring: Spring? = null

    // 是否发送拦截
    private var mIsIntercept = false
    // 首次触摸坐标
    private var mInitDownX = 0f
    private var mInitDownY = 0f
    // 上一次触摸的坐标
    private var mLastX = 0f
    private var mLastY = 0f

    // rebound动画开始执行坐标及旋转角度
    var mAnimStartX = 0f
    var mAnimStartY = 0f
    var mAnimStartRotation = 0f

    init {
        val configuration = ViewConfiguration.get(stackCardsView.context)
        mTouchSlop = configuration.scaledTouchSlop
        mMaxVelocity = configuration.scaledMaximumFlingVelocity
        mMinVelocity = configuration.scaledMinimumFlingVelocity

        springSystem = SpringSystem.create()
    }

    /**
     * 重新寻找当前可拖动的View
     * 特别是在ViewGroup整体刷新的时候原先的mTouchChild已经不在该ViewGroup上，所以要重新获取
     */
    private fun findTouchChild() {
        val index = stackCardsView.indexOfChild(mTouchChild)
        // 将下一个View作为mTouchChild
        val next = index +1
        mTouchChild = if (next < stackCardsView.childCount) stackCardsView.getChildAt(next) else null
        // 当前选中的View的起始位置不会发生变化，所以只需要获取一次就行
        if (mTouchChild != null && !mInitPropSetted) {
            mInitPropSetted = true

            mChildInitX = mTouchChild!!.x
            mChildInitY = mTouchChild!!.y
        }
    }

    fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val x = ev!!.x
        val y = ev.y
        if (mTouchChild == null) {
            return false
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!ViewDragHelperUtils.isTouchOnView(mTouchChild, x, y)) {
                    return false
                }
                mInitDownX  = x
                mInitDownY  = y
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (!mIsIntercept) {
                    // 判断滑动距离是否允许拦截
                    if ((Math.abs(x- mInitDownX) < mTouchSlop!! && Math.abs(y-mInitDownY)< mTouchSlop!!)
                            || !checkDirection(mLastX-mInitDownX, mLastY-mInitDownY)) {
                        mLastX = x
                        mLastY = y
                        return mIsIntercept
                    }
                    mIsIntercept = true
                }
                mLastX = x
                mLastY = y
            }
        }
        return mIsIntercept
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
                if (!mIsIntercept) {
                    // 判断当前滑动距离是否允许拦截
                    if ((Math.abs(x- mInitDownX) < mTouchSlop!! && Math.abs(y-mInitDownY)< mTouchSlop!!)
                            || !checkDirection(mLastX-mInitDownX, mLastY-mInitDownY)) {
                        mLastX = x
                        mLastY = y
                        return true
                    }
                    mIsIntercept = true
                }
                // 根据位移绘制当前选中的View以及剩余的View
                updateTouchChild(x - mLastX, y - mLastY)
                // 将剩下未滑动的视图进行调整
                onCoverScrolled(mTouchChild!!)
                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                // 手指松开后，判断下一步是还原还是移除
                doAnim()
            }
        }
        return true
    }

    private fun resetTouch() {
        mIsIntercept = false
    }

    /**
     * 手指松开后，判断下一步是还原还是移除
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
     * 根据位移绘制当前选中的View以及剩余的View
     */
    private fun updateTouchChild(dx: Float, dy: Float) {
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
    }

    /**
     * 根据当前选中的视图移动比例，修改其他视图状态
     */
    private fun onCoverScrolled(movingView: View) {
        val selfInfo = calcScrollInfo(movingView)
        // 根据当前选中视图移动比例后更新剩余的视图
        stackCardsView.updateChildren(selfInfo.progress, movingView)
    }

    /**
     * 获取当前选中视图移动比例信息
     */
    private fun calcScrollInfo(movingView: View) : ScrollInfo {
        val x = movingView.x - mChildInitX
        val y = movingView.y - mChildInitY
        val distance = Math.sqrt((x*x+y*y).toDouble())
        return if (distance> stackCardsView.getDismissDistance()) {
            ScrollInfo(1f)
        }
        else {
            ScrollInfo(distance.toFloat() / stackCardsView.getDismissDistance())
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

    fun checkDirection(dx: Float, dy: Float): Boolean {
        // 获取滑动限定范围
        val swipeDirection = (mTouchChild!!.layoutParams as StackCardsView.LayoutParams).swipeDirection
        when (swipeDirection) {
            StackCardsView.DIRECTION.Swipe_ALL -> return true
            StackCardsView.DIRECTION.Swipe_Left -> return Math.abs(dx) * SLOPE > Math.abs(dy) && dx < 0
            StackCardsView.DIRECTION.Swipe_Top -> return Math.abs(dy) * SLOPE > Math.abs(dx) && dy < 0
            StackCardsView.DIRECTION.Swipe_Right -> return Math.abs(dx) * SLOPE > Math.abs(dy) && dx > 0
            StackCardsView.DIRECTION.Swipe_Bottom -> return Math.abs(dy) * SLOPE > Math.abs(dx) && dy > 0
        }
        return false
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
        stackCardsView.tryAppendChild()
        findTouchChild()
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
            val height = stackCardsView.height
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
                stackCardsView.removeCover()
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
        findTouchChild()
    }
}

data class ScrollInfo(var progress: Float)