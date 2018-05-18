package com.renyu.architecturedemo.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.databinding.library.baseAdapters.BR
import com.renyu.architecturedemo.R
import com.renyu.architecturedemo.databinding.ActivitySearchBinding
import com.renyu.architecturedemo.mvvm.base.BaseActivity
import com.renyu.architecturedemo.mvvm.impl.EventListener
import com.renyu.architecturedemo.mvvm.model.SearchBeans
import com.renyu.architecturedemo.mvvm.vm.SearchViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchAdapter, SearchViewModel>(), EventListener {

    private val beans: ArrayList<SearchBeans> by lazy {
        ArrayList<SearchBeans>()
    }

    private val adapter: SearchAdapter by lazy {
        SearchAdapter(beans, this)
    }

    override fun onClickListener(view: View, value: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key", value)
        startActivity(intent)
    }

    override fun initViews() = R.layout.activity_search

    override fun variableId() = BR.adapter

    override fun model() = adapter

    override fun viewModel() = SearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initParams() {

    }

    override fun loadData() {
        viewDataBinding?.rvSearch?.post {
            viewModel?.getRemoteValue()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(bean: List<SearchBeans>) {
        beans.addAll(bean)
        adapter.notifyDataSetChanged()
    }
}