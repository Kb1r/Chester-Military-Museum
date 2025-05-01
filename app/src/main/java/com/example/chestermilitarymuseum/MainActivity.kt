package com.example.chestermilitarymuseum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout
    private lateinit var inflater: LayoutInflater
    private lateinit var homeView: View
    private lateinit var settingsView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        container = findViewById(R.id.container)
        inflater = LayoutInflater.from(this)

        // Inflate views
        homeView = inflater.inflate(R.layout.home_layout, container, false)
        settingsView = inflater.inflate(R.layout.settings_layout, container, false)

        showHome() // load home screen initially

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
        container.removeAllViews()
        container.addView(homeView)

        val btnMap = homeView.findViewById<LinearLayout>(R.id.btnMap)
        val btnTickets = homeView.findViewById<LinearLayout>(R.id.btnTickets)
        val btnGiftShop = homeView.findViewById<LinearLayout>(R.id.btnGiftShop)
        val btnContact = homeView.findViewById<LinearLayout>(R.id.btnContact)
        val startTourBox = homeView.findViewById<LinearLayout>(R.id.startTourBox)
        val qrPopup = homeView.findViewById<LinearLayout>(R.id.qrPopup)

        btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        btnTickets.setOnClickListener {
            val url =
                "https://cheshiremilitarymuseum.org.uk/shop/?ixwpst[product_cat][]=40&title=1&excerpt=1&content=1&categories=1&attributes=1&tags=1&sku=1&ixwpsf[taxonomy][product_cat][show]=set&ixwpsf[taxonomy][product_cat][multiple]=0&ixwpsf[taxonomy][product_cat][filter]=1"
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        btnGiftShop.setOnClickListener {
            val url =
                "https://cheshiremilitarymuseum.org.uk/shop/?title=1&excerpt=1&content=1&categories=1&attributes=1&tags=1&sku=1&ixwpsf[taxonomy][product_cat][show]=set&ixwpsf[taxonomy][product_cat][multiple]=0&ixwpsf[taxonomy][product_cat][filter]=1"
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        btnContact.setOnClickListener {
            startActivity(Intent(this, ContactFormActivity::class.java))
        }

        startTourBox.setOnClickListener {
            qrPopup.visibility = if (qrPopup.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    private fun showView(view: View) {
        container.removeAllViews()
        container.addView(view)
    }
}
