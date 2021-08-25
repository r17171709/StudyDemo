package com.renyu.swiperefreshlayoutdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.swiperefreshlayoutdemo.view.LoadingDrawable
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val drawable = LoadingDrawable(
            getDrawable(R.mipmap.ic_loading_sun)!!,
            getDrawable(R.mipmap.ic_loading_cloud)!!
        )
        imageView.setImageDrawable(drawable)
        imageView.post {
            drawable.start()
        }
    }
}