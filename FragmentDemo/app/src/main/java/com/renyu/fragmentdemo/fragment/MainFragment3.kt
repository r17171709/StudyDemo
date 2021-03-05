package com.renyu.fragmentdemo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.renyu.fragmentdemo.R
import com.renyu.fragmentdemo.databinding.FragmentMain3Binding

class MainFragment3() : Fragment() {
    private var binding: FragmentMain3Binding? = null

    private val fragment31 by lazy { MainFragment31(R.layout.fragment_main31, arguments?.getInt("position")!!) }
    private val fragment32 by lazy { MainFragment32(R.layout.fragment_main32, arguments?.getInt("position")!!) }

    companion object {
        fun getInstance(position: Int): MainFragment3 {
            val fragment = MainFragment3()
            val bundle = Bundle()
            bundle.putInt("position", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMain3Binding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.tvMain30.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            if (fragment32.isAdded) {
                transaction.hide(fragment32)
            }
            if (!fragment31.isAdded) {
                transaction.add(R.id.frame_main30, fragment31).commit()
            } else {
                if (fragment31.isVisible) {
                    transaction.hide(fragment31).commit()
                } else {
                    transaction.show(fragment31).commit()
                }
            }
        }
        binding!!.tvMain300.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            if (fragment31.isAdded) {
                transaction.hide(fragment31)
            }
            if (!fragment32.isAdded) {
                transaction.add(R.id.frame_main30, fragment32).commit()
            } else {
                if (fragment32.isVisible) {
                    transaction.hide(fragment32).commit()
                } else {
                    transaction.show(fragment32).commit()
                }
            }
        }
        if (arguments?.getInt("position") == 1) {
            binding!!.tvMain30.text = "点击1"
            binding!!.tvMain300.text = "点击2"
        } else if (arguments?.getInt("position") == 2) {
            binding!!.tvMain30.text = "点击3"
            binding!!.tvMain300.text = "点击4"
        }
    }

    override fun onResume() {
        super.onResume()
        check()
    }

    override fun onPause() {
        super.onPause()
        check()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        check()
    }

    private fun check() {
        if (isResumed && !isHidden) {
            Log.d(
                "TAGTAGTAG",
                "${this.javaClass.simpleName}  ${arguments?.getInt("position")} Visible"
            )
            fragment31.changeChildVisible(true)
            fragment32.changeChildVisible(true)
        } else {
            Log.d(
                "TAGTAGTAG",
                "${this.javaClass.simpleName}  ${arguments?.getInt("position")} InVisible"
            )
            fragment31.changeChildVisible(false)
            fragment32.changeChildVisible(false)
        }
    }
}