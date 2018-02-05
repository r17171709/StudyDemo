package com.house365.android8

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.provider.Settings
import com.renyu.commonlibrary.baseact.BaseActivity

class MainActivity : BaseActivity() {
    override fun setStatusBarColor() = Color.BLACK

    override fun setStatusBarTranslucent() = 0

    override fun loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!checkFloatPermission()) {
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

    private fun checkFloatPermission() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appOpsMgr = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), packageName)
            return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Settings.canDrawOverlays(this)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            var cls = Class.forName("android.content.Context")
            val declaredField = cls.getDeclaredField("APP_OPS_SERVICE")
            declaredField.isAccessible = true
            var obj = declaredField.get(cls) as? String ?: return false
            val str2 = obj
            obj = cls.getMethod("getSystemService", String::class.java).invoke(this, str2)  as String
            cls = Class.forName("android.app.AppOpsManager")
            val declaredField2 = cls.getDeclaredField("MODE_ALLOWED")
            declaredField2.isAccessible = true
            val checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String::class.java)
            val result = checkOp.invoke(obj, 24, Binder.getCallingUid(), packageName) as Int
            return result == declaredField2.getInt(cls)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true
        }
        return false
    }
}
