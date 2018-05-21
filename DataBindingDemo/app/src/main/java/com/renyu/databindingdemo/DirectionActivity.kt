package com.renyu.databindingdemo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.databindingdemo.databinding.ActivityDirectionBinding
import com.renyu.databindingdemo.model.Direction
import kotlinx.android.synthetic.main.activity_direction.*

class DirectionActivity : AppCompatActivity() {

    val direction: Direction by lazy {
        Direction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val directionBinding = DataBindingUtil.setContentView<ActivityDirectionBinding>(this, R.layout.activity_direction)
        directionBinding.direction = direction

        btn_direction.setOnClickListener {
            direction.direction["N"] = "North"
            direction.direction["S"] = "South"
            direction.direction["E"] = "East"
            direction.direction["W"] = "West"
            direction.choice.set("S")
        }
    }
}