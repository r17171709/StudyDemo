package com.example.administrator.gyroscopeimagedemo.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class GyroscopeAngel : SensorEventListener {
    private var timestamp = 0.toLong()

    private val NS2S = 1.0f / 1000000000.0f

    private val angle = longArrayOf(0.toLong(), 0.toLong(), 0.toLong())

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            if (timestamp == 0L) {
                // 从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
                timestamp = p0.timestamp
                return
            }
            // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
            val dT = (p0.timestamp -timestamp) * NS2S
            // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
            angle[0] += (p0.values[0] * dT).toLong()
            angle[1] += (p0.values[1] * dT).toLong()
            angle[2] += (p0.values[2] * dT).toLong()
            // 将弧度转化为角度
            val anglex = Math.toDegrees(angle[0].toDouble()).toFloat()
            val angley = Math.toDegrees(angle[1].toDouble()).toFloat()
            val anglez = Math.toDegrees(angle[2].toDouble()).toFloat()
            timestamp = p0.timestamp
        }
    }
}