package com.example.renyu.frescodemo.libs

import android.graphics.Bitmap
import android.graphics.Matrix
import com.facebook.common.references.CloseableReference
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
import com.facebook.imagepipeline.request.BasePostprocessor

/**
 * Created by Administrator on 2017/6/20.
 */
class CutProcess(val mBeginXPercent: Float, val mBeginYPercent: Float, val mCutWidthPercent: Float, val mCutHeightPercent: Float) : BasePostprocessor() {

    override fun process(sourceBitmap: Bitmap?, bitmapFactory: PlatformBitmapFactory?): CloseableReference<Bitmap>? {
        val width = sourceBitmap!!.width
        val height = sourceBitmap!!.height
        val beginX = width*mBeginXPercent
        val beginY = height*mBeginYPercent
        val endX = width*mCutWidthPercent
        val endY = height*mCutHeightPercent
        val bmp: CloseableReference<Bitmap> = bitmapFactory!!.createBitmap(scale(sourceBitmap), beginX.toInt(), beginY.toInt(), endX.toInt(), endY.toInt())
//        val bmp: CloseableReference<Bitmap> = bitmapFactory!!.createBitmap(CommonUtils.getDecodeRegion(CommonUtils.getBitmapArray(sourceBitmap, 100), sourceBitmap.width/2, sourceBitmap.height/2))
        return CloseableReference.cloneOrNull(bmp)
    }

    fun scale(sourceBitmap: Bitmap?): Bitmap {
        val matrix: Matrix = Matrix()
        matrix.postScale(1.1f, 1.1f, 0.5f, 0.5f)
        return Bitmap.createBitmap(sourceBitmap!!, 0, 0, sourceBitmap!!.width, sourceBitmap!!.height, matrix, true)
    }
}