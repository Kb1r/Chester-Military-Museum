package com.example.chestermilitarymuseum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.SettingsLayoutBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var baseBinding: ActivityBaseBinding
    private lateinit var settingsBinding: SettingsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        settingsBinding = SettingsLayoutBinding.inflate(LayoutInflater.from(this))

        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)

        // Show SettingsLayout inside the base layout's container
        baseBinding.container.removeAllViews()
        baseBinding.container.addView(settingsBinding.root)

        setupExpandableSections()
        setupLanguageDropdown()
        setupListeners()

        // Handle navigation
        baseBinding.bottomNavigation.selectedItemId = R.id.navigation_settings
        baseBinding.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_home) {
                finish()
                true
            } else false
        }
    }

    private fun setupExpandableSections() {
        settingsBinding.toggleAccessibility.setOnClickListener {
            toggleVisibility(settingsBinding.sectionAccessibility)
        }

        settingsBinding.toggleFont.setOnClickListener {
            toggleVisibility(settingsBinding.sectionFont)
        }

        settingsBinding.toggleLanguage.setOnClickListener {
            settingsBinding.spinnerLanguage.visibility =
                if (settingsBinding.spinnerLanguage.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    private fun setupLanguageDropdown() {
        val languages = arrayOf("English", "French", "German", "Spanish")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        settingsBinding.spinnerLanguage.adapter = adapter
    }

    private fun setupListeners() {
        settingsBinding.switchAudioGuide.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Audio Guide: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        settingsBinding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Notifications: ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        settingsBinding.btnSmallFont.setOnClickListener {
            Toast.makeText(this, "Small font selected", Toast.LENGTH_SHORT).show()
        }

        settingsBinding.btnMediumFont.setOnClickListener {
            Toast.makeText(this, "Medium font selected", Toast.LENGTH_SHORT).show()
        }

        settingsBinding.btnLargeFont.setOnClickListener {
            Toast.makeText(this, "Large font selected", Toast.LENGTH_SHORT).show()
        }

        settingsBinding.btnResetSettings.setOnClickListener {
            settingsBinding.accessibilityGroup.check(R.id.rbDefault)
            settingsBinding.switchAudioGuide.isChecked = false
            settingsBinding.switchNotifications.isChecked = false
            settingsBinding.spinnerLanguage.setSelection(0)
            Toast.makeText(this, "Settings reset", Toast.LENGTH_SHORT).show()
        }
    }
}
