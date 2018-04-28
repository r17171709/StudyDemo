package com.example.tantan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.rebound.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2018/4/24.
 */
class ReboundActivity : AppCompatActivity() {

    val springSystem = SpringSystem.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iv_main.setOnClickListener {
            val scaleSpring = springSystem.createSpring()
            scaleSpring.currentValue = 0.0
            scaleSpring.springConfig = SpringConfig.fromOrigamiTensionAndFriction(40.0, 5.0)
            scaleSpring.addListener(object : SpringListener {
                override fun onSpringUpdate(spring: Spring?) {
                    val mappedValue = SpringUtil.mapValueFromRangeToRange(spring?.currentValue!!, 0.0, 1.0, 0.5, 1.0).toFloat()

                    println(mappedValue)
                    iv_main.scaleX = mappedValue
                    iv_main.scaleY = mappedValue
                }

                override fun onSpringEndStateChange(spring: Spring?) {

                }

                override fun onSpringAtRest(spring: Spring?) {
                    scaleSpring.setAtRest()
                }

                override fun onSpringActivate(spring: Spring?) {

                }
            })

            scaleSpring.endValue = 1.0
        }
    }
}