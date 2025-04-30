package com.example.chestermilitarymuseum

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val container = findViewById<FrameLayout>(R.id.container)
        val mapView = layoutInflater.inflate(R.layout.map_layout, container, false)
        container.addView(mapView)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // remove back button

        val titleView = toolbar.findViewById<TextView>(R.id.headerTitle)
        titleView.text = "Map"

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
