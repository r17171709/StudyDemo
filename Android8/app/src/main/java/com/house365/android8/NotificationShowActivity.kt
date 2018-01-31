package com.renyu.androidcommonlibrary.activity

import android.graphics.Color
import com.house365.android8.R
import com.renyu.commonlibrary.baseact.BaseActivity

/**
 * Created by renyu on 2018/1/31.
 */
class NotificationShowActivity : BaseActivity() {
    override fun initParams() {
        println("NotificationShowActivity")
    }

    override fun initViews() = R.layout.activity_notificationshow

    override fun loadData() {

    }

    override fun setStatusBarColor() = Color.BLACK

    override fun setStatusBarTranslucent() = 0
}