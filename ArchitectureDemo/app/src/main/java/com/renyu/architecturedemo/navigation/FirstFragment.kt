package com.renyu.architecturedemo.navigation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.renyu.architecturedemo.R
import com.renyu.architecturedemo.databinding.FragmentFirstBinding
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewDataBinding = DataBindingUtil.inflate<FragmentFirstBinding>(inflater, R.layout.fragment_first, container, false)
        viewDataBinding.searchBeans = SearchBeans()
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_first.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "value")
            NavHostFragment.findNavController(this).navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }
    }
}