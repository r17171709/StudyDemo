package com.renyu.architecturedemo.repos

import com.renyu.architecturedemo.impl.RetrofitImpl
import com.renyu.architecturedemo.util.RetrofitService

object Repos {
    fun getNews() =
        RetrofitService.create(RetrofitImpl::class.java).getNews()
}