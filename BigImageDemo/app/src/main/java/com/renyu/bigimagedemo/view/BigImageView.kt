package com.renyu.bigimagedemo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import java.io.InputStream

class BigImageView : View, GestureDetector.OnGestureListener, View.OnTouchListener {
    private var mBitmap: Bitmap? = null
    private var mOptions: BitmapFactory.Options? = null
    private var mRect: Rect? = null
    private var mBitmapRegionDecoder: BitmapRegionDecoder? = null
    private var mScale = 1.0f

    private var imageWidth = 0
    private var imageHeight = 0

    private var viewWidth = 0
    private var viewHeight = 0

    private var mGestureDetector: GestureDetector? = null

    private var mScroller: Scroller? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mOptions = BitmapFactory.Options()
        mRect = Rect()
        mGestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
        mScroller = Scroller(context)
    }

    fun setImage(inputStream: InputStream) {
        mOptions!!.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, mOptions)

        imageWidth = mOptions!!.outWidth
        imageHeight = mOptions!!.outHeight

        mOptions!!.inMutable = true
        mOptions!!.inPreferredConfig = Bitmap.Config.RGB_565
        mOptions!!.inJustDecodeBounds = false

        // isShareable为false代表图片资源不共享
        mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)

        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        viewWidth = measuredWidth
        viewHeight = measuredHeight

        mScale = viewWidth * 1.0f / imageWidth

        mRect!!.left = 0
        mRect!!.top = 0
        mRect!!.right = imageWidth

        mRect!!.bottom = (viewHeight / mScale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mBitmapRegionDecoder == null) {
            return
        }

        mOptions!!.inBitmap = mBitmap
//        calcuteInSampleSize()
        mBitmap = mBitmapRegionDecoder!!.decodeRegion(mRect!!, mOptions!!)
        val matrix = Matrix()
        matrix.setScale(mScale, mScale)

        // 如果使用采样，则需使用此方法将图片的宽高重新调整一下
//        matrix.setScale(mScale * mOptions!!.inSampleSize, mScale * mOptions!!.inSampleSize)

        canvas.drawBitmap(mBitmap!!, matrix, null)

        // 默认图片占用内存大小 47021472
        // 使用采样后占用内存大小 2937384
        Log.d("TAGTAGTAG", "${mBitmap!!.allocationByteCount}")
    }

    private fun calcuteInSampleSize() {
        var inSampleSize = 1
        while (imageWidth / measuredWidth > inSampleSize) {
            inSampleSize *= 2
        }
        mOptions!!.inSampleSize = inSampleSize
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        mRect!!.offset(0, distanceY.toInt())
        if (mRect!!.top <= 0) {
            mRect!!.top = 0
            mRect!!.bottom = (viewHeight / mScale).toInt()
        }
        if (mRect!!.bottom >= imageHeight) {
            mRect!!.top = imageHeight - (viewHeight / mScale).toInt()
            mRect!!.bottom = imageHeight
        }
        invalidate()
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        // fling范围为 0到imageHeight - (viewHeight / mScale).toInt()之间
        mScroller!!.fling(
            0,
            mRect!!.top,
            0,
            -velocityY.toInt(),
            0,
            0,
            0,
            imageHeight - (viewHeight / mScale).toInt()
        )
        return false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return mGestureDetector!!.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()

        if (mScroller!!.computeScrollOffset()) {
            // fling时会触发到此处进行滚动
            mRect!!.top = mScroller!!.currY
            mRect!!.bottom = mRect!!.top + (viewHeight / mScale).toInt()
            invalidate()
        }
    }
}