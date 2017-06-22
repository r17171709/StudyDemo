package com.example.renyu.frescodemo

import android.graphics.Bitmap
import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.renyu.frescodemo.libs.LoadingProgressDrawable
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var image_1: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image_1 = findViewById(R.id.image_1)

        image_fresco1!!.autoPlayAnimations(true)
        image_fresco1!!.progressBarImage(LoadingProgressDrawable(this))
        image_fresco1!!.post {
            image_fresco1!!.draweeViewUrl(Uri.parse("http://pooyak.com/p/progjpeg/jpegload.cgi?o=1"))
//            image_fresco1!!.draweeViewUrl(Uri.parse("res:///"+R.mipmap.rotate))
//            image_fresco1!!.draweeViewUrl(Uri.parse("http://img.huofar.com/data/jiankangrenwu/shizi.gif"))
//            image_fresco1!!.setRoundDraweeViewUrl(Uri.parse("file:///"+CommonUtils.IMAGEDIR+ File.separator + "testgif.gif"))
        }
        image_fresco2.post {
            image_fresco2!!.hierarchy.setActualImageFocusPoint(PointF(1f, 1f))
            val imageRequest: ImageRequest=ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498699595&di=f1966f6a12a7a2cfaffad00c6e86f327&imgtype=jpg&er=1&src=http%3A%2F%2Fbbsatt.yineitong.com%2Fforum%2F2011%2F03%2F25%2F110325164993a2105258f0d314.jpg"))
                    .setResizeOptions(ResizeOptions(200, 100)).build()
            val draweeController: DraweeController=Fresco.newDraweeControllerBuilder()
                    .setTapToRetryEnabled(true)
                    .setImageRequest(imageRequest)
                    .setAutoPlayAnimations(true).build()
            image_fresco2!!.controller=draweeController
        }

        image_fresco3.post {
            val layoutParams: LinearLayout.LayoutParams = image_fresco3!!.layoutParams as LinearLayout.LayoutParams
            layoutParams.width=600
            layoutParams.height=400

            val imageRequest: ImageRequest=ImageRequestBuilder
                    .newBuilderWithSource(Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498699595&di=f1966f6a12a7a2cfaffad00c6e86f327&imgtype=jpg&er=1&src=http%3A%2F%2Fbbsatt.yineitong.com%2Fforum%2F2011%2F03%2F25%2F110325164993a2105258f0d314.jpg"))
                    // 获取缩略图
//                    .newBuilderWithSource(Uri.parse("file:///storage/emulated/0/DCIM/Camera/20170529_163007.jpg"))
//                    .setLocalThumbnailPreviewsEnabled(true)

                    // 图片后处理
//                    .setRotationOptions(RotationOptions.forceRotation(0))
//                    .setPostprocessor(CutProcess(0f, 0f, 0.5f, 0.5f))
                    .build()
            val requests: Array<ImageRequest> = arrayOf(ImageRequest.fromUri(Uri.parse("res:///"+R.mipmap.rotate)), imageRequest)
            val draweeController: DraweeController=Fresco.newDraweeControllerBuilder()
                    // 优先加载低分辨率图片，再加载高分辨率图片
//                    .setLowResImageRequest(ImageRequest.fromUri(Uri.parse("res:///"+R.mipmap.rotate)))
//                    .setImageRequest(imageRequest).setAutoPlayAnimations(true)
                    // 优先加载先回调完成的图片
                    .setFirstAvailableImageRequests(requests)
                    .setOldController(image_fresco3!!.controller).build()
            image_fresco3!!.controller=draweeController


        }

        // 单独获取图片
        val imageRequest=ImageRequest.fromUri("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498120837836&di=1de86b4660416d5649eef412f54e76d0&imgtype=0&src=http%3A%2F%2Fimage.elegantliving.ceconline.com%2F320000%2F320100%2F20110815_03_52.jpg")
        var dataSource: DataSource<CloseableReference<CloseableImage>> =
                Fresco.getImagePipeline().fetchDecodedImage(imageRequest, this)
        dataSource.subscribe(CustomerBitmapDataSubscriber(), CallerThreadExecutor.getInstance())

        // 清除缓存
//        Fresco.getImagePipeline().clearMemoryCaches()
//        Fresco.getImagePipeline().clearDiskCaches()
//        Fresco.getImagePipeline().clearCaches()
    }

    class CustomerBitmapDataSubscriber : BaseBitmapDataSubscriber() {
        override fun onFailureImpl(p0: DataSource<CloseableReference<CloseableImage>>?) {

        }

        override fun onNewResultImpl(p0: Bitmap?) {
            println("获取成功1 "+p0!!.width+" "+p0!!.height)
        }
    }
}

