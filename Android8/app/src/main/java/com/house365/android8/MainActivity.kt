package com.house365.android8

import android.graphics.Color
import android.os.Build
import android.provider.Settings
import com.renyu.commonlibrary.baseact.BaseActivity
import android.content.Intent
import android.net.Uri
import android.os.Handler


class MainActivity : BaseActivity() {
    override fun setStatusBarColor() = Color.BLACK

    override fun setStatusBarTranslucent() = 0

    override fun loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(applicationContext)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
                startActivity(intent)
                return
            }
        }
        Handler().postDelayed({
            val floatWindow = FloatWindow(this)
            floatWindow.showFloatWindow()
        }, 3000)
    }

    override fun initParams() {

    }

    override fun initViews() = R.layout.activity_main
}
