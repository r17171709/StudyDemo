package com.renyu.route_runtime

import android.content.Context
import android.content.Intent
import android.net.Uri

object Route {
    // 编译期间生产的总映射表类
    private const val GENERATED_MAPPING = "com.renyu.gradledemo.mapping.RouteMapping"

    // 存储所有映射表
    private val mapping: HashMap<String, String> = HashMap()

    fun init() {
        val clazz = Class.forName(GENERATED_MAPPING)
        val method = clazz.getMethod("get")
        val allMapping = method.invoke(null) as Map<String, String>
        mapping.putAll(allMapping)
    }

    fun goto(context: Context, url: String) {
        val uri = Uri.parse(url)
        uri.scheme
        uri.host
        uri.path
        uri.query

        mapping.forEach {
            if (it.key == "${uri.scheme}://${uri.host}${uri.path}") {
                val intent = Intent(context, Class.forName(it.value))
                uri.query?.split("&")?.forEach {
                    val param = it.split("=")
                    intent.putExtra(param[0], param[1])
                }
                context.startActivity(intent)
            }
        }
    }
}