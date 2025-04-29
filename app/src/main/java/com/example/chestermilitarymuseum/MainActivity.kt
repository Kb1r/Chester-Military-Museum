package com.example.chestermilitarymuseum

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load activity_base.xml (your full layout shell)
        setContentView(R.layout.activity_base)

        // Inject the home page layout into the container
        val container = findViewById<FrameLayout>(R.id.container)
        val homeView = LayoutInflater.from(this).inflate(R.layout.home_layout, container, false)
        container.addView(homeView)
    }
}
