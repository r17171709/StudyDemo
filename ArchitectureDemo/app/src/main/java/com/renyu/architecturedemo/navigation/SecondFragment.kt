package com.renyu.architecturedemo.navigation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renyu.architecturedemo.R
import com.renyu.architecturedemo.databinding.FragmentSecondBinding
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import org.jetbrains.anko.support.v4.toast

class SecondFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewDataBinding = DataBindingUtil.inflate<FragmentSecondBinding>(inflater, R.layout.fragment_second, container, false)
        viewDataBinding.searchBeans = SearchBeans()
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            toast(it.getString("key"))
        }
    }
}