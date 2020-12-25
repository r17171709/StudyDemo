package com.renyu.changeskindemo.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import java.io.File

class SkinEngine private constructor() {
    private lateinit var context: Context
    private lateinit var resources: Resources
    private lateinit var outPackageName: String

    companion object {
        val instance: SkinEngine by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SkinEngine() }
    }

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    /**
     * path: 外部资源apk的路径
     */
    fun load(path: String) {
        val file = File(path)
        if (!file.exists()) {
            return
        }
        try {
            val packageManager = context.packageManager
            val packageInfo =
                packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
            outPackageName = packageInfo!!.packageName
            // 通过反射获取AssetManager，用来加载外面的资源包
            val assetManager = AssetManager::class.java.newInstance()
            val method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, path)
            // 最终创建出一个"外部资源包" resources。可以让我们的app有能力加载外部的资源文件
            resources = Resources(
                assetManager,
                context.resources.displayMetrics,
                context.resources.configuration
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 外部资源包里面的颜色
     */
    fun getColor(resName: String): Int {
        // 通过定义的名字获取到外部资源的内存地址，进而获取到相应的颜色
        val colorId = resources.getIdentifier(resName, "color", outPackageName)
        return resources.getColor(colorId)
    }
}