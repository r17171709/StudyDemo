package com.renyu.bigimagedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.renyu.bigimagedemo.databinding.ActivityMainBinding
import com.renyu.bigimagedemo.util.ImageCache

class MainActivity : AppCompatActivity() {
    private val beans by lazy { ArrayList<String>() }

    private val adapter by lazy { MainAdapter(beans) }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.ivBig.setImage(assets.open("big.png"))

//        loadImage()

        for (i in 0 until 100) {
            beans.add("image$i")
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.isNestedScrollingEnabled = true
        binding.rvMain.adapter = adapter
    }

//    private fun loadImage() {
//        val instance = ImageCache.getInstance(applicationContext)
//        val mBitmap = instance.getBitmapFromMemory("ic_launcher")
//        if (mBitmap == null) {
//            val reuseable =
//                instance.getReuseable(200 * getDensity().toInt(), 200 * getDensity().toInt())
//            val realBitmap = instance.resizeBitmap(assets.open("world.jpg"), reuseable)
//            instance.putBitmap2Memory("ic_launcher", realBitmap)
//            binding.ivNormal.setImageBitmap(realBitmap)
//        } else {
//            binding.ivNormal.setImageBitmap(mBitmap)
//        }
//    }
//
//    private fun getDensity(): Float {
//        val displayMetrics = applicationContext.resources.displayMetrics
//        return displayMetrics.density
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        ImageCache.getInstance(applicationContext).clearMemory()
//    }
}