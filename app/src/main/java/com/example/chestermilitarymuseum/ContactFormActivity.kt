package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContactFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val container = findViewById<FrameLayout>(R.id.container)
        val contactView = layoutInflater.inflate(R.layout.contact_form, container, false)
        container.addView(contactView)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Update header title
        val titleView = toolbar.findViewById<TextView>(R.id.headerTitle)
        titleView.text = "Contact Us"

        // Clickable logo to go home
        val logoImage = toolbar.findViewById<ImageView>(R.id.logoImage)
        logoImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // Bottom nav handling
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
