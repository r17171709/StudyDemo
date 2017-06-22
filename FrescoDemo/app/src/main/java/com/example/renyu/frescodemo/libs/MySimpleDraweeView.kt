package com.example.renyu.frescodemo.libs

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

/**
 * Created by Administrator on 2017/5/31.
 */

/**
 * autoPlayAnimation 是否自动播放gif图
 * progressiveRenderingEnabled 是否开启渐进式加载图片
 * controllerListener 下载监听器
 * cacheChoice 加载图片的缓存格式
 * requestLevel 图片最低允许从哪层缓存中取数据
 * scaleType 实际加载的图片的伸缩样式
 * fadeDuration 进度条和占位符图片逐渐消失、加载完成的图片逐渐显示的时间间隔
 * placeHolderDrawable 图片预加载时候显示的占位图
 * overlayImageDrawable 在加载完成的图片表层覆盖一张图片的图片资源
 * progressBarImage 加载时进度
 * rotate 旋转角度
 */
data class MySimpleDraweeViewParams(var autoPlayAnimation: Boolean = false,
                                    var progressiveRenderingEnabled: Boolean = true,
                                    var controllerListener: ControllerListener<ImageInfo>,
                                    var cacheChoice: ImageRequest.CacheChoice = ImageRequest.CacheChoice.DEFAULT,
                                    var requestLevel: ImageRequest.RequestLevel = ImageRequest.RequestLevel.FULL_FETCH,
                                    var scaleType: ScalingUtils.ScaleType = ScalingUtils.ScaleType.CENTER_CROP,
                                    var fadeDuration: Int = 0,
                                    var placeHolderDrawable: Drawable = ColorDrawable(Color.TRANSPARENT),
                                    var failureImageDrawable: Drawable = ColorDrawable(Color.TRANSPARENT),
                                    var retryImageDrawable: Drawable = ColorDrawable(Color.TRANSPARENT),
                                    var overlayImageDrawable: Drawable = ColorDrawable(Color.TRANSPARENT),
                                    var progressBarImage: Drawable?,
                                    var rotate: Int = 0)

class MySimpleDraweeView : SimpleDraweeView {

    // 图片加载形状区分
    enum class ImageType {
        NORMAL, ROUND // 基本样式、圆形样式
    }

    var mySimpleDraweeViewParams: MySimpleDraweeViewParams ?=null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mySimpleDraweeViewParams = MySimpleDraweeViewParams(controllerListener = DefaultBaseControllerListener(), progressBarImage = null)
    }

    /**
     * 设置是否自动播放gif图
     */
    fun autoPlayAnimations(autoPlayAnimation: Boolean) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.autoPlayAnimation=autoPlayAnimation
        return this
    }

    /**
     * 是否开启渐进式图片加载
     */
    fun progressiveRenderingEnabled(progressiveRenderingEnabled: Boolean) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.progressiveRenderingEnabled=progressiveRenderingEnabled
        return this
    }

    /**
     * 设置下载监听器
     */
    fun defaultBaseControllerListener(controllerListener: ControllerListener<ImageInfo>) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.controllerListener=controllerListener
        return this
    }

    /**
     * 设置加载图片的缓存格式
     */
    fun cacheChoice(cacheChoice: ImageRequest.CacheChoice) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.cacheChoice=cacheChoice
        return this
    }

    /**
     * 设置图片最低允许从哪层缓存中取数据
     */
    fun requestLevel(requestLevel: ImageRequest.RequestLevel) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.requestLevel=requestLevel
        return this
    }

    /**
     * 设置图片缩放类型
     */
    fun scakeType(scaleType: ScalingUtils.ScaleType) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.scaleType=scaleType
        return this
    }

    /**
     * 设置图片加载动画时间
     */
    fun fadeDuration(fadeDuration: Int) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.fadeDuration=fadeDuration
        return this
    }

    /**
     * 设置图片预加载时显示的图片
     */
    fun placeHolderDrawable(placeHolderDrawable: Drawable) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.placeHolderDrawable=placeHolderDrawable
        return this
    }

    /**
     * 设置加载失败时显示的图片
     */
    fun failureImageDrawable(failureImageDrawable: Drawable) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.failureImageDrawable=failureImageDrawable
        return this
    }

    /**
     * 设置加载失败重试时显示的图片
     */
    fun retryImageDrawable(retryImageDrawable: Drawable) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.retryImageDrawable=retryImageDrawable
        return this
    }

    /**
     * 设置覆盖层图片
     */
    fun overlayImageDrawable(overlayImageDrawable: Drawable) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.overlayImageDrawable=overlayImageDrawable
        return this
    }

    /**
     * 设置加载进度条图片
     */
    fun progressBarImage(progressBarImage: Drawable) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.progressBarImage=progressBarImage
        return this
    }

    /**
     * 设置旋转角度
     */
    fun rotate(rotate: Int) : MySimpleDraweeView {
        mySimpleDraweeViewParams!!.rotate=rotate
        return this
    }

    /**
     * 加载基本图片
     */
    fun draweeViewUrl(url: String) : MySimpleDraweeView {
        return draweeViewUrl(Uri.parse(url))
    }

    /**
     * 加载基本图片
     */
    fun draweeViewUrl(url: Uri) : MySimpleDraweeView {
        return loadImage(url, ImageType.NORMAL)
    }

    /**
     * 加载圆形图片
     */
    fun roundDraweeViewUrl(url: String) : MySimpleDraweeView {
        loadImage(Uri.parse(url), ImageType.ROUND)
        return this
    }

    /**
     * 加载圆形图片
     */
    fun roundDraweeViewUrl(url: Uri) : MySimpleDraweeView {
        loadImage(url, ImageType.ROUND)
        return this
    }

    fun loadImage(url: Uri, type: ImageType) : MySimpleDraweeView {
        if (type == ImageType.NORMAL) {
            super.setHierarchy(setNormalHierarchy(context))
        }
        else if (type == ImageType.ROUND) {
            super.setHierarchy(setRoundHierarchy(context))
        }
        var pipelineDraweeControllerBuilder: PipelineDraweeControllerBuilder=Fresco.newDraweeControllerBuilder()
        pipelineDraweeControllerBuilder.autoPlayAnimations = mySimpleDraweeViewParams!!.autoPlayAnimation
        pipelineDraweeControllerBuilder.controllerListener = mySimpleDraweeViewParams!!.controllerListener
        pipelineDraweeControllerBuilder.tapToRetryEnabled = true
        pipelineDraweeControllerBuilder.imageRequest=getImageRequest(url)
        super.setController(pipelineDraweeControllerBuilder.build())
        return this
    }

    /**
     * 加载监听器
     */
    private class DefaultBaseControllerListener : BaseControllerListener<ImageInfo>() {

        // 图片加载成功时触发的方法
        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
            super.onFinalImageSet(id, imageInfo, animatable)
            println("width:${imageInfo!!.width}  height:${imageInfo!!.height}")
        }

        // 加载渐进式图片时回调的方法
        override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
            super.onIntermediateImageSet(id, imageInfo)
            println(imageInfo.toString())
        }

        // 图片加载失败时回调的方法
        override fun onFailure(id: String?, throwable: Throwable?) {
            super.onFailure(id, throwable)
        }
    }

    private fun getImageRequest(url: Uri) : ImageRequest {
        var imageRequestBuilder: ImageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(url)
        imageRequestBuilder.cacheChoice = mySimpleDraweeViewParams!!.cacheChoice
        imageRequestBuilder.lowestPermittedRequestLevel = mySimpleDraweeViewParams!!.requestLevel
        imageRequestBuilder.isLocalThumbnailPreviewsEnabled = true // 图片请求会在访问本地图片时先返回一个缩略图
        imageRequestBuilder.isProgressiveRenderingEnabled = mySimpleDraweeViewParams!!.progressiveRenderingEnabled
        imageRequestBuilder.resizeOptions = ResizeOptions(measuredWidth, measuredHeight)
        imageRequestBuilder.rotationOptions = RotationOptions.forceRotation(mySimpleDraweeViewParams!!.rotate)
        return imageRequestBuilder.build()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 设置各种图像的层级
     */
    private fun setNormalHierarchy(context: Context) : GenericDraweeHierarchy {
        val hierarchy: GenericDraweeHierarchy = this.hierarchy
//        val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.ani_rotate)
        if (mySimpleDraweeViewParams!!.progressBarImage!=null) {
            hierarchy.setProgressBarImage(mySimpleDraweeViewParams!!.progressBarImage)
            // 系统默认横向线性进度条ProgressBarDrawable
//            hierarchy.setProgressBarImage(ProgressBarDrawable())
        }

        var roundingParams: RoundingParams = RoundingParams.fromCornersRadius(30f)
        hierarchy.roundingParams = roundingParams
        hierarchy.fadeDuration= mySimpleDraweeViewParams!!.fadeDuration
        hierarchy.setOverlayImage(mySimpleDraweeViewParams!!.overlayImageDrawable)
        hierarchy.actualImageScaleType=mySimpleDraweeViewParams!!.scaleType
        hierarchy.setPlaceholderImage(mySimpleDraweeViewParams!!.placeHolderDrawable, mySimpleDraweeViewParams!!.scaleType)
        hierarchy.setFailureImage(mySimpleDraweeViewParams!!.placeHolderDrawable, mySimpleDraweeViewParams!!.scaleType)
        hierarchy.setRetryImage(mySimpleDraweeViewParams!!.placeHolderDrawable, mySimpleDraweeViewParams!!.scaleType)
        return hierarchy
    }

    /**
     * 设置圆形样式
     */
    private fun setRoundHierarchy(context: Context) : GenericDraweeHierarchy {
        var hierarchy: GenericDraweeHierarchy=setNormalHierarchy(context)
        hierarchy.roundingParams = RoundingParams.asCircle()
        return hierarchy
    }
}
