package com.renyu.architecturedemo.mvvm

import com.renyu.architecturedemo.BR
import com.renyu.architecturedemo.R
import com.renyu.architecturedemo.databinding.AdapterSearchBinding
import com.renyu.architecturedemo.mvvm.base.BaseAdapter
import com.renyu.architecturedemo.mvvm.impl.EventListener
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import java.util.*

class SearchAdapter(beans: ArrayList<SearchBeans>, eventListener: EventListener) : BaseAdapter<AdapterSearchBinding, SearchBeans>(beans, eventListener) {
    override fun dataVariableId(): Int {
        return BR.beans
    }

    override fun eventListenerVariableId(): Int {
        return BR.eventListener
    }

    override fun initViews() = R.layout.adapter_search
}