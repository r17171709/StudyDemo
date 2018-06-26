package com.example.administrator.gyroscopeimagedemo.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.gyroscopeimagedemo.R
import com.example.administrator.gyroscopeimagedemo.ui.adapter.MainAdapter
import com.example.administrator.gyroscopeimagedemo.utils.GyroscopeObserver
import com.example.administrator.gyroscopeimagedemo.utils.GyroscopeTransFormation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    val images: ArrayList<String> by lazy {
        ArrayList<String>()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        Picasso.get()
                .load("http://7b1g8u.com1.z0.glb.clouddn.com/house1.png")
                .transform(GyroscopeTransFormation(1080, 600))
                .into(iv_image)

//        Glide.with(iv_image.context)
//                .load("http://7b1g8u.com1.z0.glb.clouddn.com/house1.png")
//                .into(iv_image)

        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house4.png")
        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house1.png")
        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house2.png")
        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house3.png")
        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house4.png")
        images.add("http://7b1g8u.com1.z0.glb.clouddn.com/house1.png")

        rv_image.setHasFixedSize(true)
        rv_image.scrollToPosition(1)
        rv_image.addOnItemChangedListener { _, adapterPosition ->
            if (adapterPosition == 0) {
                rv_image.scrollToPosition(4)
            }
            if (adapterPosition == 5) {
                rv_image.scrollToPosition(1)
            }
        }
        val adapter = MainAdapter(requireContext(), images)
        rv_image.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        GyroscopeObserver.getInstance().register(requireContext())
    }

    override fun onPause() {
        super.onPause()
        GyroscopeObserver.getInstance().unRegister()
    }
}
