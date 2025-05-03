package com.example.chestermilitarymuseum

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.HomeLayoutBinding
import com.example.chestermilitarymuseum.databinding.SettingsLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var homeBinding: HomeLayoutBinding
    private lateinit var settingsBinding: SettingsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Clicking the logo goes home
        binding.logoImage.setOnClickListener {
            showHome()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }

        // Inflate home and settings layouts
        val inflater = LayoutInflater.from(this)
        homeBinding = HomeLayoutBinding.inflate(inflater)
        settingsBinding = SettingsLayoutBinding.inflate(inflater)

        showHome()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showHome()
                    true
                }
                R.id.navigation_settings -> {
                    showView(settingsBinding.root)
                    true
                }
                else -> false
            }
        }
    }

    private fun showHome() {
        binding.container.removeAllViews()
        binding.container.addView(homeBinding.root)

        // Setup all buttons on the home layout
        homeBinding.btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        homeBinding.btnTickets.setOnClickListener {
            val url = "https://cheshiremilitarymuseum.org.uk/shop/?ixwpst[product_cat][]=40..."
            openWeb(url)
        }

        homeBinding.btnGiftShop.setOnClickListener {
            val url = "https://cheshiremilitarymuseum.org.uk/shop/?title=1..."
            openWeb(url)
        }

        homeBinding.btnContact.setOnClickListener {
            startActivity(Intent(this, ContactFormActivity::class.java))
        }

        homeBinding.startTourBox.setOnClickListener {
            val isVisible = homeBinding.qrPopup.visibility == View.VISIBLE
            homeBinding.qrPopup.visibility = if (isVisible) View.GONE else View.VISIBLE
        }
    }

    private fun showView(view: View) {
        binding.container.removeAllViews()
        binding.container.addView(view)
    }

    private fun openWeb(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }
}
