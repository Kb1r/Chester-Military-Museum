package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.HomeLayoutBinding
import com.example.chestermilitarymuseum.databinding.SettingsLayoutBinding

class MainActivity : AppCompatActivity() {

    // ViewBindings
    private lateinit var binding: ActivityBaseBinding
    private lateinit var homeBinding: HomeLayoutBinding
    private lateinit var settingsBinding: SettingsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar without back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Logo returns to Home
        binding.logoImage.setOnClickListener {
            showHome()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }

        // Inflate child layouts
        val inflater = LayoutInflater.from(this)
        homeBinding = HomeLayoutBinding.inflate(inflater)
        settingsBinding = SettingsLayoutBinding.inflate(inflater)

        // Default screen
        showHome()

        // Bottom Navigation
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

        // HOME PAGE BUTTONS
        homeBinding.btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        homeBinding.btnTickets.setOnClickListener {
            openWeb("https://cheshiremilitarymuseum.org.uk/shop/?ixwpst[product_cat][]=40")
        }

        homeBinding.btnGiftShop.setOnClickListener {
            openWeb("https://cheshiremilitarymuseum.org.uk/shop/?title=1")
        }

        homeBinding.btnContact.setOnClickListener {
            startActivity(Intent(this, ContactFormActivity::class.java))
        }

        // SHOW QR POPUP
        homeBinding.startTourBox.setOnClickListener {
            homeBinding.qrPopup.visibility = View.VISIBLE
            homeBinding.qrOverlay.visibility = View.VISIBLE
        }

        // TAP OUTSIDE TO HIDE POPUP
        homeBinding.qrOverlay.setOnClickListener {
            homeBinding.qrPopup.visibility = View.GONE
            homeBinding.qrOverlay.visibility = View.GONE
        }

        // CLOSE BUTTON TO HIDE POPUP
        homeBinding.btnClosePopup.setOnClickListener {
            homeBinding.qrPopup.visibility = View.GONE
            homeBinding.qrOverlay.visibility = View.GONE
        }

        // SUBMIT CODE BUTTON HANDLER
        homeBinding.btnSubmitCode.setOnClickListener {
            val code = homeBinding.codeInput.text.toString().trim()
            val correctCode = "123"

            if (code == correctCode) {
                val intent = Intent(this, IntroductionInfoActivity::class.java)
                startActivity(intent)

                // Optionally hide the popup
                homeBinding.qrPopup.visibility = View.GONE
                homeBinding.qrOverlay.visibility = View.GONE
                homeBinding.codeInput.setText("")
            } else {
                Toast.makeText(this, "Invalid code. Please try again.", Toast.LENGTH_SHORT).show()
            }
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
