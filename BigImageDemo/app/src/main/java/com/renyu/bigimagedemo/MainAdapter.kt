package com.renyu.bigimagedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.renyu.bigimagedemo.databinding.AdapterMainBinding
import com.renyu.bigimagedemo.util.ImageCache

class MainAdapter(private val beans: ArrayList<String>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(viewBinding: AdapterMainBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val _viewBinding = viewBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val imageView = holder._viewBinding.ivAdapter
        imageView.tag = beans[holder.layoutPosition]
        loadImage(imageView, holder.layoutPosition)
    }

    override fun getItemCount() = beans.size

    private fun loadImage(imageView: ImageView, position: Int) {
        val instance = ImageCache.getInstance(imageView.context.applicationContext)
        val mBitmap = instance.getBitmapFromMemory(imageView.tag.toString())
        if (mBitmap == null) {
            val reuseable =
                instance.getReuseable(
                    200 * getDensity(imageView).toInt(),
                    200 * getDensity(imageView).toInt()
                )
            val realBitmap =
                instance.resizeBitmap(
                    imageView.context.assets.open("list${position % 10}.jpeg"),
                    reuseable
                )
            instance.putBitmap2Memory(imageView.tag.toString(), realBitmap)
            imageView.setImageBitmap(realBitmap)
        } else {
            imageView.setImageBitmap(mBitmap)
        }
    }

    private fun getDensity(imageView: ImageView): Float {
        val displayMetrics = imageView.context.applicationContext.resources.displayMetrics
        return displayMetrics.density
    }
}