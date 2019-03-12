package com.renyu.flutterdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.map.MapStatusUpdate
import com.baidu.mapapi.model.LatLng


class DemoMapView : LinearLayout {
    @JvmField
    var mapView: MapView?
    private var baiduMap: BaiduMap
    private var locationClient: LocationClient? = null
    private var locationClientOption: LocationClientOption? = null

    constructor(context: Context): super(context) {
        SDKInitializer.initialize(context.applicationContext)
        LayoutInflater.from(context).inflate(R.layout.map_view, this, true)

        mapView = findViewById(R.id.bmapView)
        mapView?.showZoomControls(false)
        baiduMap = mapView!!.map
        baiduMap.isMyLocationEnabled = true
        baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18f))
    }
    constructor(context: Context, attributeSet: AttributeSet): this(context)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): this(context, attributeSet)

    fun startLocation() {
        locationClient = LocationClient(context)
        locationClientOption = LocationClientOption()
        locationClientOption?.isOpenGps = true
        locationClientOption?.coorType = "bd09ll"
        locationClientOption?.scanSpan = 1000
        locationClient?.locOption = locationClientOption
        locationClient?.registerLocationListener(object : BDAbstractLocationListener() {
            override fun onReceiveLocation(p0: BDLocation?) {
                if (p0 == null || mapView == null) {
                    return
                }
                val myLocationData = MyLocationData.Builder()
                        .accuracy(p0.radius)
                        .latitude(p0.latitude)
                        .longitude(p0.longitude)
                        .direction(p0.direction)
                        .build()
                baiduMap.setMyLocationData(myLocationData)
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(LatLng(p0.latitude, p0.longitude), 18f))
                locationClient?.stop()
            }
        })
        locationClient?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        locationClient?.stop()
        baiduMap.isMyLocationEnabled = false
        mapView?.onDestroy()
        mapView = null
    }
}