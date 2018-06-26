package com.example.administrator.gyroscopeimagedemo.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.administrator.gyroscopeimagedemo.ui.view.GyroscopeImageView
import java.util.*

class GyroscopeObserver<T : GyroscopeImageView> : SensorEventListener {

    companion object {
        private var gyroscopeObserver: GyroscopeObserver<in GyroscopeImageView>? = null

        @JvmStatic
        fun getInstance() : GyroscopeObserver<in GyroscopeImageView> {
            if (gyroscopeObserver == null) {
                synchronized(GyroscopeObserver::class.java) {
                    if (gyroscopeObserver == null) {
                        gyroscopeObserver = GyroscopeObserver()
                    }
                }
            }
            return gyroscopeObserver!!
        }
    }

    // 陀螺仪触发持续时间
    var mLastTimestamp = 0L

    val views: LinkedList<T> = LinkedList()

    private val NS2S = 1.0f / 1000000000.0f
    // 陀螺仪在X、Y轴旋转的最大弧度
    // The value must between (0, π/2].
    private val mMaxRotateRadian = Math.PI / 2

    private var sensorManager: SensorManager? = null

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type != Sensor.TYPE_GYROSCOPE) {
            return
        }
        if (mLastTimestamp == 0L) {
            mLastTimestamp = p0.timestamp
            return
        }
        val rotateX = Math.abs(p0.values[0])
        val rotateY = Math.abs(p0.values[1])
        val rotateZ = Math.abs(p0.values[2])
        // X轴方向偏移量更多
        if (rotateX > rotateY + rotateZ) {

        }
        // Y轴方向偏移量更多
        else if (rotateY > rotateX + rotateZ) {

        }
        val dt = (p0.timestamp - mLastTimestamp) * NS2S * 2.0f
        views.forEach {
            it.mRotateRadianY += p0.values[1] * dt
            it.mRotateRadianX += p0.values[0] * dt
            if (it.mRotateRadianY > mMaxRotateRadian) {
                it.mRotateRadianY = mMaxRotateRadian.toFloat()
            }
            else if (it.mRotateRadianY < -mMaxRotateRadian) {
                it.mRotateRadianY = -mMaxRotateRadian.toFloat()
            }
            if (it.mRotateRadianX > mMaxRotateRadian) {
                it.mRotateRadianX = mMaxRotateRadian.toFloat()
            }
            else if (it.mRotateRadianX < -mMaxRotateRadian) {
                it.mRotateRadianX = -mMaxRotateRadian.toFloat()
            }
            // 注意此处，X与Y方向是反过来的
            it.updateProgress((it.mRotateRadianY / mMaxRotateRadian).toFloat(), (it.mRotateRadianX / mMaxRotateRadian).toFloat())
        }
        mLastTimestamp = p0.timestamp
    }

    /**
     * 注册陀螺仪传感器
     */
    fun register(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        // 陀螺仪灵敏参数  SENSOR_DELAY_FASTEST = 0; SENSOR_DELAY_GAME = 1; SENSOR_DELAY_NORMAL = 3; SENSOR_DELAY_UI = 2;
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
        // 重置持续时间参数
        mLastTimestamp = 0
    }

    /**
     * 解绑陀螺仪传感器
     */
    fun unRegister() {
        sensorManager?.unregisterListener(this)
        sensorManager = null
    }

    /**
     * 添加ImageView
     */
    fun addGyroscopeImageViews(view: T) {
        if (!views.contains(view))
            views.add(view)
    }

    fun removeGyroscopeImageViews(view: T) {
        if (!views.contains(view))
            views.remove(view)
    }
}