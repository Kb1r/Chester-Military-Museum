package com.example.chestermilitarymuseum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout
    private lateinit var inflater: LayoutInflater
    private lateinit var homeView: View
    private lateinit var settingsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load base layout with toolbar + bottom nav
        setContentView(R.layout.activity_base)

        // Init
        container = findViewById(R.id.container)
        inflater = LayoutInflater.from(this)

        // Inflate both views once
        homeView = inflater.inflate(R.layout.home_layout, container, false)
        settingsView = inflater.inflate(R.layout.settings_layout, container, false)

        // Load home by default
        showView(homeView)

        // Set up bottom nav item selection
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showView(homeView)
                    true
                }
                R.id.navigation_settings -> {
                    showView(settingsView)
                    true
                }
                else -> false
            }
        }
    }

    private fun showView(view: View) {
        container.removeAllViews()
        container.addView(view)
    }
}
