package com.renyu.canvasdemo

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.canvasdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewCircle.post {
            val animator1 = ValueAnimator.ofFloat(0f, 2f)
            animator1.duration = 2000
            animator1.addUpdateListener {
                binding.viewCircle.rotatePercent = it.animatedValue.toString().toFloat()
                binding.viewCircle.invalidate()
            }

            val animator2 = ValueAnimator.ofFloat(0f, 2f)
            animator2.duration = 2000
            animator2.addUpdateListener {
                binding.viewCircle.xPercent = it.animatedValue.toString().toFloat()
                binding.viewCircle.invalidate()
            }

            val animator3 = ValueAnimator.ofFloat(0f, 1f, 0f)
            animator3.duration = 2000
            animator3.addUpdateListener {
                binding.viewCircle.pointPercent = it.animatedValue.toString().toFloat()
                binding.viewCircle.invalidate()
            }

            val animator4 = ValueAnimator.ofFloat(0f, 2f)
            animator4.duration = 2000
            animator4.addUpdateListener {
                binding.viewCircle.gouPercent = it.animatedValue.toString().toFloat()
                binding.viewCircle.invalidate()
            }

            val set = AnimatorSet()
            set.play(animator1).before(animator2)
            set.play(animator2).before(animator3)
            set.play(animator3).before(animator4)
            set.start()
        }
    }
}