package com.renyu.architecturedemo.mvvm

import com.android.databinding.library.baseAdapters.BR
import com.renyu.architecturedemo.R
import com.renyu.architecturedemo.databinding.ActivityDetailBinding
import com.renyu.architecturedemo.mvvm.base.BaseActivity
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import com.renyu.architecturedemo.mvvm.vm.DetailViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, SearchBeans, DetailViewModel>() {

    private val searchBeans: SearchBeans by lazy {
        SearchBeans()
    }

    override fun initViews() = R.layout.activity_detail

    override fun variableId() = BR.searchBeans

    override fun model() = searchBeans

    override fun viewModel() =  DetailViewModel(searchBeans)

    override fun initParams() {

    }

    override fun loadData() {
        viewModel?.update(intent.getStringExtra("key"))
    }
}