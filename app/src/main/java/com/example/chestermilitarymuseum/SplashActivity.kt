package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.widget.ImageView

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.splashLogo)

        // Fade out animation on logo
        val fadeOut = AlphaAnimation(1f, 0f).apply {
            duration = 1000
            startOffset = 1000
            fillAfter = true
        }
        logo.startAnimation(fadeOut)

        // After delay, go to MainActivity with fade transition
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 2000)
    }
}
