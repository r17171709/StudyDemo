package com.example.tantan.view

import android.content.Context
import android.database.Observable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.blankj.utilcode.util.SizeUtils
import com.example.tantan.R

class StackCardsView: FrameLayout {

    private val INVALID_SIZE = Int.MIN_VALUE

    // 可见卡片个数
    var maxVisibleCnt = 0
    // 层叠时相对前一张卡片的边距
    var edgeHeight = 0
    // 层叠时相对前一张卡片的缩放比例
    var scaleFactor = 1f
    // 层叠时相对前一张卡片的渐变比例
    var alphaFactor = 1f
    // 滑动时判定可以消失的最小滑动距离比例
    var dismissFactor = 0.4f
    // 卡片宽度
    private var itemWidth = 0
    // 卡片高度
    private var itemHeight = 0

    // 默认缩放渐变位移区间参数
    var mScaleArray: Array<Float?>? = null
    var mAlphaArray: Array<Float?>? = null
    var mTranslationYArray: Array<Int?>? = null

    // 提供给新添加View使用的Rect
    var mLastLeft = 0
    var mLastTop = 0
    var mLastRight = 0
    var mLastBottom = 0

    // 是否需要重新配置卡片的位置
    var mNeedAdjustChildren = false

    var mDataObserver: InnerDataObserver? = null

    var adapter: Adapter? = null

    var listener: OnCardSwipedListener? = null

    val mTouchHelper: SwipeTouchHelper by lazy {
        SwipeTouchHelper(this)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context, attributeSet, defStyleAttr)
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet, 0)
    }
    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    private fun init(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) {
        isChildrenDrawingOrderEnabled = true

        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.StackCardsView, defStyleAttr, 0)
        maxVisibleCnt = typeArray.getInt(R.styleable.StackCardsView_maxVisibleCnt, 3)
        edgeHeight = typeArray.getInt(R.styleable.StackCardsView_edgeHeight, SizeUtils.dp2px(8f))
        scaleFactor = typeArray.getFloat(R.styleable.StackCardsView_scaleFactor, 0.8f)
        alphaFactor = typeArray.getFloat(R.styleable.StackCardsView_alphaFactor, 0.8f)
        dismissFactor = typeArray.getFloat(R.styleable.StackCardsView_dismissFactor, 0.4f)
        itemWidth = typeArray.getDimensionPixelSize(R.styleable.StackCardsView_itemWidth, INVALID_SIZE)
        if (itemWidth == INVALID_SIZE) {
            throw IllegalArgumentException("必须设置itemWidth")
        }
        itemHeight = typeArray.getDimensionPixelSize(R.styleable.StackCardsView_itemHeight, INVALID_SIZE)
        if (itemHeight == INVALID_SIZE) {
            throw IllegalArgumentException("必须设置itemHeight")
        }
        typeArray.recycle()
    }

    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        return childCount - 1 - i
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mNeedAdjustChildren) {
            mNeedAdjustChildren = false
            adjustChildren()
            // 数据刷新之后重新获取mTouchChild
            mTouchHelper.onChildChanged()
        }
        val count = childCount
        if (count>0) {
            mLastLeft = getChildAt(count - 1).left
            mLastTop = getChildAt(count - 1).top
            mLastRight = getChildAt(count - 1).right
            mLastBottom = getChildAt(count - 1).bottom
        }
    }

    /**
     * 对所有卡片进行初始化配置，并对缩放、渐变、位移属性区间参数配置完成
     */
    private fun adjustChildren() {
        val count = childCount
        if (count == 0) {
            return
        }
        var scale = 0.0
        var alpha = 0.0
        var translationY = 0
        // 当前最大可见卡片个数
        var maxVisibleIndex = Math.min(count, maxVisibleCnt) - 1
        mScaleArray = arrayOfNulls(count)
        mAlphaArray = arrayOfNulls(count)
        mTranslationYArray = arrayOfNulls(count)
        // 对所有卡片进行初始化配置，并对缩放、渐变、位移属性区间参数配置完成
        // 先对可见卡片进行配置
        for (i in 0..maxVisibleIndex) {
            val child = getChildAt(i)

            scale = Math.pow(scaleFactor.toDouble(), i.toDouble())
            mScaleArray!![i] = scale.toFloat()
            alpha = Math.pow(alphaFactor.toDouble(), i.toDouble())
            mAlphaArray!![i] = alpha.toFloat()
            translationY = ((1 - scale) * child.measuredHeight/2 + edgeHeight*i).toInt()
            mTranslationYArray!![i] = translationY

            child.scaleX = scale.toFloat()
            child.scaleY = scale.toFloat()
            child.alpha = alpha.toFloat()
            child.translationY = translationY.toFloat()
        }
        // 再对不可见卡片进行配置
        for (i in (maxVisibleIndex+1) until count) {
            val child = getChildAt(i)

            // 不可见卡片直接沿用最后一张可见卡片数据
            mScaleArray!![i] = scale.toFloat()
            mAlphaArray!![i] = alpha.toFloat()
            mTranslationYArray!![i] = translationY

            child.scaleX = scale.toFloat()
            child.scaleY = scale.toFloat()
            // 透明度设置为0
            child.alpha = 0f
            child.translationY = translationY.toFloat()
        }
    }

    /**
     * 重新添加全部子视图
     */
    private fun initChildren() {
        val count = adapter?.getCount()
        removeAllViewsInLayout()
        if (count != 0 && count != null) {
            var maxVisibleIndex = Math.min(count, maxVisibleCnt)
            // 多加一个动画就没有那么突兀了
            for (i in 0..maxVisibleIndex) {
                addViewInLayout(adapter?.getView(i, null, this), -1, LayoutParams(itemWidth, itemHeight, Gravity.CENTER, DIRECTION.Swipe_Left), true)
            }
        }
        mNeedAdjustChildren = true
        requestLayout()
    }

    object DIRECTION {
        const val Swipe_ALL = 0
        const val Swipe_Left = 1
        const val Swipe_Top = 2
        const val Swipe_Right = 3
        const val Swipe_Bottom = 4
    }

    inner class LayoutParams(width: Int, height: Int, gravity: Int, swipeDirection: Int) : FrameLayout.LayoutParams(width, height, gravity) {
        val swipeDirection = swipeDirection
    }

    private fun safeRegisterObserver() {
        safeUnRegisterObserver()
        if (mDataObserver == null) {
            mDataObserver = InnerDataObserver()
        }
        if (adapter != null) {
            adapter!!.registerDataObserver(mDataObserver!!)
        }
    }

    private fun safeUnRegisterObserver() {
        if (adapter != null && mDataObserver != null) {
            adapter!!.unRegisterDataObserver(mDataObserver!!)
            mDataObserver = null
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        safeUnRegisterObserver()
    }

    abstract class Adapter {
        private val ob = CardDataObservable()

        // 必须要通过此种方式才能跨包引用到内部类，并且内部类可以使用到外部类的方法
        fun registerDataObserver(innerDataObserver: InnerDataObserver) {
            ob.registerObserver(innerDataObserver)
        }
        fun unRegisterDataObserver(innerDataObserver: InnerDataObserver) {
            ob.unregisterObserver(innerDataObserver)
        }

        abstract fun getCount(): Int
        abstract fun getView(position: Int, convertView: View?, parent: ViewGroup): View
        // 数据变化的时候调用
        fun notifyDataSetChanged() {
            ob.notifyDataSetChanged()
        }
        // 数据增加的时候调用
        fun notifyItemInserted(position: Int) {
            ob.notifyItemInserted(position)
        }
        // 数据被删除的时候调用
        fun notifyItemRemoved(position: Int) {
            ob.notifyItemRemoved(position)
        }
    }

    fun setCardAdapter(adapter: Adapter) {
        this.adapter = adapter
        safeRegisterObserver()
        initChildren()
    }

    inner class InnerDataObserver {
        fun notifyDataSetChanged() {
            initChildren()
        }

        fun notifyItemInserted(position: Int) {

        }

        fun notifyItemRemoved(position: Int) {
            val view = getChildAt(position)
            removeViewInLayout(view)
            requestLayout()
        }
    }

    private class CardDataObservable : Observable<InnerDataObserver>() {
        fun notifyDataSetChanged() {
            for (i in 0 until mObservers.size) {
                mObservers[i].notifyDataSetChanged()
            }
        }

        fun notifyItemInserted(position: Int) {
            for (i in 0 until mObservers.size) {
                mObservers[i].notifyItemInserted(position)
            }
        }

        fun notifyItemRemoved(position: Int) {
            for (i in 0 until mObservers.size) {
                mObservers[i].notifyItemRemoved(position)
            }
        }
    }

    /**
     * 获取最小判定消失距离
     */
    fun getDismissDistance() : Float = width * dismissFactor

    /**
     * 添加下一个View
     */
    fun tryAppendChild() {
        val count = childCount
        if (adapter?.getCount()!! > childCount) {
            // adapter中数据集与当前ViewGroup中的视图顺序是一对一的，假设当前视图中有三个子View，则取下一个视图的索引正好是3
            val view = adapter?.getView(count, null, this)
            addViewInLayout(view, -1,  LayoutParams(itemWidth, itemHeight, Gravity.CENTER), true)
            view?.layout(mLastLeft, mLastTop, mLastRight, mLastBottom)
        }
    }

    /**
     * 调整未拖动的子View的动画属性
     */
    fun updateChildren(progress: Float, scrollingView: View) {
        val count = childCount
        // 一般情况下都是当前可见的第一个视图是scrollingView，所以未发送滚动的View都是第二个视图开始
        val startIndex = indexOfChild(scrollingView) + 1
        if (startIndex > count) {
            return
        }
        // 动画执行初始值
        var originScale: Double
        var originAlpha: Double
        var originTranslationY: Int
        // 动画执行上限
        var maxScale: Double
        var maxAlpha: Double
        var maxTranslationY: Int
        for (i in startIndex until count) {
            val view = getChildAt(i)
            val originIndex = i - startIndex + 1
            val maxIndex = i - startIndex

            originScale = mScaleArray!![originIndex]!!.toDouble()
            maxScale = mScaleArray!![maxIndex]!!.toDouble()
            view.scaleX = ((maxScale - originScale) * progress + originScale).toFloat()
            view.scaleY = ((maxScale - originScale) * progress + originScale).toFloat()

            originAlpha = mAlphaArray!![originIndex]!!.toDouble()
            maxAlpha = mAlphaArray!![maxIndex]!!.toDouble()
            view.alpha = ((maxAlpha - originAlpha) * progress + originAlpha).toFloat()

            originTranslationY = mTranslationYArray!![originIndex]!!
            maxTranslationY = mTranslationYArray!![maxIndex]!!
            view.translationY = ((maxTranslationY - originTranslationY) * progress + originTranslationY)
        }
    }

    /**
     * 可移动视图被删除
     */
    fun removeCover() {
        listener?.onCardDismiss()
    }

    interface OnCardSwipedListener {
        fun onCardDismiss()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mTouchHelper.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mTouchHelper.onTouchEvent(event)
    }

    override fun addView(child: View?) {
        throw UnsupportedOperationException("不支持addView(child: View?)")
    }

    override fun addView(child: View?, index: Int) {
        throw UnsupportedOperationException("不支持addView(child: View?, index: Int)")
    }

    override fun addView(child: View?, width: Int, height: Int) {
        throw UnsupportedOperationException("不支持addView(child: View?, width: Int, height: Int)")
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        throw UnsupportedOperationException("不支持addView(child: View?, params: ViewGroup.LayoutParams?)")
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        throw UnsupportedOperationException("不支持addView(child: View?, index: Int, params: ViewGroup.LayoutParams?)")
    }

    override fun removeView(view: View?) {
        throw UnsupportedOperationException("不支持removeView(view: View?)")
    }

    override fun removeViewAt(index: Int) {
        throw UnsupportedOperationException("不支持removeViewAt(index: Int)")
    }

    override fun removeViews(start: Int, count: Int) {
        throw UnsupportedOperationException("不支持removeViews(start: Int, count: Int)")
    }

    override fun removeAllViews() {
        throw UnsupportedOperationException("不支持removeAllViews()")
    }
}