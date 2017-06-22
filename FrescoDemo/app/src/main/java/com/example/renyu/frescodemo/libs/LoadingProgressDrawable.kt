package com.example.renyu.frescodemo.libs

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import com.example.renyu.frescodemo.R
import com.facebook.drawee.drawable.DrawableUtils

/**
 * Created by Administrator on 2017/6/20.
 */
class LoadingProgressDrawable() : Drawable() {

    val images: IntArray = intArrayOf(R.drawable.load_progress_0,
            R.drawable.load_progress_1,
            R.drawable.load_progress_2,
            R.drawable.load_progress_3,
            R.drawable.load_progress_4,
            R.drawable.load_progress_5,
            R.drawable.load_progress_6,
            R.drawable.load_progress_7,
            R.drawable.load_progress_8,
            R.drawable.load_progress_9,
            R.drawable.load_progress_10,
            R.drawable.load_progress_11,
            R.drawable.load_progress_12)

    var mLevel: Int = 0

    var context: Context? = null

    var options: BitmapFactory.Options? = null

    var paint: Paint? = null

    constructor(context: Context) : this() {
        this.context = context
        options = BitmapFactory.Options()
        paint = Paint()
        paint!!.isAntiAlias = true
    }

    override fun draw(p0: Canvas?) {
        val bmp: Bitmap = BitmapFactory.decodeResource(context!!.resources, images[getIndex()], options)
        val imageWidth = bmp.width
        val imageHeight = bmp.height
        val drawableWidth = bounds.width()
        val drawableHeight = bounds.height()
        val left = (drawableWidth-imageWidth)/2
        val top = (drawableHeight-imageHeight)/2
        p0!!.drawBitmap(bmp, left.toFloat(), top.toFloat(), paint!!)
    }

    override fun setAlpha(p0: Int) {
        paint!!.alpha = p0
    }

    override fun getOpacity(): Int {
        return DrawableUtils.getOpacityFromColor(Color.TRANSPARENT)
    }

    override fun setColorFilter(p0: ColorFilter?) {
        paint!!.setColorFilter(p0)
    }

    override fun onLevelChange(level: Int): Boolean {
        this.mLevel = level
        invalidateSelf()
        return true
    }

    fun getIndex(): Int {
        var index: Int = mLevel/1000
        if (index<0) {
            index=0
        }
        if (index>images.size-1) {
            index=images.size-1
        }
        return index
    }
}