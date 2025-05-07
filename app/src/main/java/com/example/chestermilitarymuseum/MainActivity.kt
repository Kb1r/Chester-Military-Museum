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
import com.google.zxing.integration.android.IntentIntegrator

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

        binding.logoImage.setOnClickListener {
            showHome()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }

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
                R.id.navigation_news -> {
                    startActivity(Intent(this, NewsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun showHome() {
        binding.container.removeAllViews()
        binding.container.addView(homeBinding.root)

        // Home buttons
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

        // Show QR popup
        homeBinding.startTourBox.setOnClickListener {
            homeBinding.qrOverlay.visibility = View.VISIBLE
            homeBinding.qrPopup.visibility = View.VISIBLE
        }

        // Close popup (✕)
        homeBinding.btnClosePopup.setOnClickListener {
            closeQrPopup()
        }

        // Tap outside to close
        homeBinding.qrOverlay.setOnClickListener {
            closeQrPopup()
        }

        // Submit manually entered code
        homeBinding.btnSubmitCode.setOnClickListener {
            val enteredCode = homeBinding.codeInput.text.toString().trim()
            if (enteredCode == "123") {
                startActivity(Intent(this, IntroductionInfoActivity::class.java))
                closeQrPopup()
            } else {
                Toast.makeText(this, "Incorrect code", Toast.LENGTH_SHORT).show()
            }
        }

        // Launch QR scanner
        homeBinding.btnLaunchQrScanner.setOnClickListener {
            IntentIntegrator(this).apply {
                setOrientationLocked(false)
                setPrompt("Scan a QR Code")
                setBeepEnabled(true)
                initiateScan()
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

    private fun closeQrPopup() {
        homeBinding.qrOverlay.visibility = View.GONE
        homeBinding.qrPopup.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            val scannedCode = result.contents.trim()
            if (scannedCode == "123") {
                startActivity(Intent(this, IntroductionInfoActivity::class.java))
                closeQrPopup()
            } else {
                Toast.makeText(this, "Invalid QR code", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
