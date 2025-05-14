package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.HomeLayoutBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var homeBinding: HomeLayoutBinding

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setHomeText() - maybe remove

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.logoImage.setOnClickListener {
            showHome()
            binding.bottomNavigation.selectedItemId = R.id.navigation_home
        }

        val inflater = LayoutInflater.from(this)
        homeBinding = HomeLayoutBinding.inflate(inflater)

        showHome()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showHome()
                    true
                }
                R.id.navigation_settings -> {
                    binding.container
                    startActivity(Intent(this, SettingsActivity::class.java))
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

        // Close popup via ✕
        homeBinding.btnClosePopup.setOnClickListener {
            homeBinding.qrOverlay.visibility = View.GONE
            homeBinding.qrPopup.visibility = View.GONE
        }

        // Tap outside to close
        homeBinding.qrOverlay.setOnClickListener {
            homeBinding.qrOverlay.visibility = View.GONE
            homeBinding.qrPopup.visibility = View.GONE
        }

        // Prevent click-through inside the popup
        homeBinding.qrPopup.setOnClickListener {
            // Consume click to prevent closing
        }

        // Submit code manually
        homeBinding.btnSubmitCode.setOnClickListener {
            val code = homeBinding.codeInput.text.toString().trim()
            if (code == "123") {
                startActivity(Intent(this, IntroductionInfoActivity::class.java))
            } else {
                Toast.makeText(this, "Incorrect code", Toast.LENGTH_SHORT).show()
            }
        }

        // Launch QR Scanner
        homeBinding.btnLaunchQrScanner.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR Code")
            integrator.setBeepEnabled(true)
            integrator.initiateScan()
        }

        //Home Text Resource Binding:
        homeBinding.tvMap.text = getString(R.string.tvMap)
        homeBinding.tvTickets.text = getString(R.string.tvTickets)
        homeBinding.tvGiftShop.text = getString(R.string.tvGiftShop)
        homeBinding.tvContactUs.text = getString(R.string.tvContactUs)
        homeBinding.startTourText.text = getString(R.string.startTourText)
        binding.headerTitle.text = getText(R.string.headerTitle)
        homeBinding.codeInput.setHint(R.string.codeInput)
        homeBinding.btnSubmitCode.text = getString(R.string.btnSubmitCode)
        homeBinding.btnLaunchQrScanner.text = getString(R.string.btnLaunchQrScanner)

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

    // QR Scan result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            if (result.contents.trim() == "MUSEUM123") {
                startActivity(Intent(this, IntroductionInfoActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid QR code", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
