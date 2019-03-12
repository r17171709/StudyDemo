package com.renyu.flutterdemo

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.StandardMessageCodec

object InspectionMapPlugin : MethodChannel.MethodCallHandler {
    fun registerWith(registry: PluginRegistry) {
        if (alreadyRegisteredWith(registry)) {
            return
        }
    }

    private fun alreadyRegisteredWith(registry: PluginRegistry): Boolean {
        val key = InspectionMapPlugin::class.java.canonicalName
        if (registry.hasPlugin(key)) {
            return true
        }
        val registrar = registry.registrarFor(key)
        registrar.platformViewRegistry().registerViewFactory("MapView",
                BMapViewFactory(StandardMessageCodec(), registrar))
        return false
    }

    override fun onMethodCall(p0: MethodCall?, p1: MethodChannel.Result?) {

    }
}