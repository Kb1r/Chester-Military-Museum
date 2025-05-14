package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.MapLayoutBinding

class MapActivity : BaseActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var mapBinding: MapLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.headerTitle.text = "Map"

        mapBinding = MapLayoutBinding.inflate(layoutInflater)
        binding.container.addView(mapBinding.root)

        setMapText()

        binding.logoImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity((Intent(this, MainActivity::class.java)))
                    true
                }
                R.id.navigation_settings -> {
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

    private fun setMapText(){
        mapBinding.tvWelcome.text = getString(R.string.tvWelcome)
        mapBinding.tvStairs.text = getString(R.string.tvStairs)
        mapBinding.tvOutdoor.text = getString(R.string.tvOutdoor)
        mapBinding.tvToilets.text = getString(R.string.tvToilets)
        mapBinding.tvLift.text = getString(R.string.tvLift)
        mapBinding.tvAccessibleToilets.text = getString(R.string.tvAccessibleToilets)
        mapBinding.tvMapLegends.text = getString(R.string.tvMapLegends)
    }

}
