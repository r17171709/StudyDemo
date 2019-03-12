package com.renyu.flutterdemo

import android.content.Context
import android.view.View
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

class BMapViewFactory(createArgsCodec: MessageCodec<Any>?, val pluginRegistrar: PluginRegistry.Registrar) : PlatformViewFactory(createArgsCodec) {
    var demoMapView: DemoMapView? = null

    init {
        demoMapView = DemoMapView(pluginRegistrar.activity())
    }

    override fun create(p0: Context?, p1: Int, p2: Any?): PlatformView {
        return object : PlatformView {
            override fun getView(): View {
                print("getView")
                if (demoMapView == null) {
                    demoMapView = DemoMapView(pluginRegistrar.activity())
                }
                demoMapView?.startLocation()
                return demoMapView!!
            }

            override fun dispose() {
                print("dispose")
            }
        }
    }
}