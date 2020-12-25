package com.renyu.changeskindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import com.renyu.changeskindemo.skin.SkinEngine
import com.renyu.changeskindemo.skin.SkinFactory

/**
 * 参考项目：https://github.com/18598925736/HookSkinDemoFromHank
 */
class MainActivity : AppCompatActivity() {
    private val skinFactory2: SkinFactory by lazy { SkinFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        skinFactory2.setDelegate(delegate)
        val layoutInflater = LayoutInflater.from(this)
        layoutInflater.factory2 = skinFactory2

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SkinEngine.instance.load(Environment.getExternalStorageDirectory().path + "/1/zsb20201221.apk")
        skinFactory2.changeSkin()
    }
}