package com.pns.podcastly.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pns.podcastly.R
import com.pns.podcastly.ui.onboarding.WelcomeTutorial

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val iv_splash_image = findViewById<ImageView>(R.id.iv_splash_image)

        Glide.with(applicationContext).load(R.drawable.splash_gif).into(iv_splash_image)

        Handler().postDelayed({
            startActivity(Intent(this, WelcomeTutorial::class.java))
            finish()
        }, 2000)
    }
}