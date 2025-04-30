package com.example.chestermilitarymuseum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout
    private lateinit var inflater: LayoutInflater
    private lateinit var homeView: View
    private lateinit var settingsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // Set up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Set up base layout
        container = findViewById(R.id.container)
        inflater = LayoutInflater.from(this)

        // Inflate layouts
        homeView = inflater.inflate(R.layout.home_layout, container, false)
        settingsView = inflater.inflate(R.layout.settings_layout, container, false)

        // Load home screen by default
        showHome()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showHome()
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

    private fun showHome() {
        showView(homeView)

        // Re-bind views every time home is loaded
        val btnMap = homeView.findViewById<LinearLayout>(R.id.btnMap)
        val btnTickets = homeView.findViewById<LinearLayout>(R.id.btnTickets)
        val btnGiftShop = homeView.findViewById<LinearLayout>(R.id.btnGiftShop)
        val btnContact = homeView.findViewById<LinearLayout>(R.id.btnContact)
        val startTourBox = homeView.findViewById<LinearLayout>(R.id.startTourBox)
        val qrPopup = homeView.findViewById<LinearLayout>(R.id.qrPopup)

        startTourBox.setOnClickListener {
            Log.d("StartTour", "Tapped")
            qrPopup.visibility = if (qrPopup.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        btnTickets.setOnClickListener {
            val url = "https://cheshiremilitarymuseum.org.uk/shop/?ixwpst[product_cat][]=40&title=1&excerpt=1&content=1&categories=1&attributes=1&tags=1&sku=1&ixwpsf[taxonomy][product_cat][show]=set&ixwpsf[taxonomy][product_cat][multiple]=0&ixwpsf[taxonomy][product_cat][filter]=1"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        btnGiftShop.setOnClickListener {
            val url = "https://cheshiremilitarymuseum.org.uk/shop/?title=1&excerpt=1&content=1&categories=1&attributes=1&tags=1&sku=1&ixwpsf[taxonomy][product_cat][show]=set&ixwpsf[taxonomy][product_cat][multiple]=0&ixwpsf[taxonomy][product_cat][filter]=1"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        btnContact.setOnClickListener {
            startActivity(Intent(this, ContactFormActivity::class.java))
        }
    }

    private fun showView(view: View) {
        container.removeAllViews()
        container.addView(view)
    }
}
