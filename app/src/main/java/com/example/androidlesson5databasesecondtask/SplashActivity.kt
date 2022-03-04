package com.example.androidlesson5databasesecondtask

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasesecondtask.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding: ActivitySplashBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivitySplashBinding.inflate(layoutInflater)
        with(binding)
        {
            // setContentView(binding.root)


            val animationFirst =
                AnimationUtils.loadAnimation(this@SplashActivity, R.anim.translate_first)
            binding.tv.startAnimation(animationFirst)
            Handler(Looper.getMainLooper()).postDelayed({
                val animationSecond =
                    AnimationUtils.loadAnimation(this@SplashActivity, R.anim.translate_second)
                binding.tv.startAnimation(animationSecond)
                tv.visibility = View.INVISIBLE
            }, 4000)


            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 5000)

        }


    }
}