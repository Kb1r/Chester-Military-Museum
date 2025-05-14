package com.example.chestermilitarymuseum

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.SettingsLayoutBinding
import java.util.Locale

// 1️⃣ Extend BaseActivity instead of AppCompatActivity
class SettingsActivity : BaseActivity() {

    private lateinit var baseBinding: ActivityBaseBinding
    private lateinit var settingsBinding: SettingsLayoutBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val languages = mapOf(
        "English" to "en",
        "French" to "fr",
        "Spanish" to "es",
        "German" to "de"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        settingsBinding = SettingsLayoutBinding.inflate(LayoutInflater.from(this))
        setContentView(baseBinding.root)
        setSupportActionBar(baseBinding.toolbar)

        // container
        baseBinding.container.removeAllViews()
        baseBinding.container.addView(settingsBinding.root)

        // init prefs
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // language spinner restore
        val currentLanguage = sharedPreferences.getString("LANGUAGE", "en")
        setupExpandableSections()
        setupLanguageDropdown(currentLanguage)
        setupListeners()
        setSettingsText()

        settingsBinding.spinnerLanguage.setSelection(
            languages.values.indexOf(currentLanguage)
        )

        // bottom nav
        baseBinding.bottomNavigation.selectedItemId = R.id.navigation_settings
        baseBinding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
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

    private fun setupExpandableSections() {
        settingsBinding.toggleAccessibility.setOnClickListener {
            toggleVisibility(settingsBinding.sectionAccessibility)
        }
        settingsBinding.toggleFont.setOnClickListener {
            toggleVisibility(settingsBinding.sectionFont)
        }
        settingsBinding.toggleLanguage.setOnClickListener {
            toggleVisibility(settingsBinding.spinnerLanguage)
        }
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    private fun setupLanguageDropdown(currentLang: String?) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            languages.keys.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        settingsBinding.spinnerLanguage.adapter = adapter

        settingsBinding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCode = languages.values.toList()[position]
                    if (selectedCode != currentLang) {
                        setLocale(selectedCode)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupListeners() {
        // Audio & Notifications unchanged
        settingsBinding.switchAudioGuide.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                "Audio Guide: ${if (isChecked) "On" else "Off"}",
                Toast.LENGTH_SHORT
            ).show()
        }
        settingsBinding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                "Notifications: ${if (isChecked) "Enabled" else "Disabled"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // 2️⃣ Font‐size buttons now persist + recreate
        settingsBinding.btnSmallFont.setOnClickListener {
            sharedPreferences.edit().putString("font_size", "small").apply()
            recreate()
        }
        settingsBinding.btnMediumFont.setOnClickListener {
            sharedPreferences.edit().putString("font_size", "medium").apply()
            recreate()
        }
        settingsBinding.btnLargeFont.setOnClickListener {
            sharedPreferences.edit().putString("font_size", "large").apply()
            recreate()
        }

        // Reset everything, including font back to medium
        settingsBinding.btnResetSettings.setOnClickListener {
            // restore defaults
            settingsBinding.accessibilityGroup.check(R.id.rbDefault)
            settingsBinding.switchAudioGuide.isChecked = false
            settingsBinding.switchNotifications.isChecked = false
            settingsBinding.spinnerLanguage.setSelection(0)

            // reset font size
            sharedPreferences.edit().putString("font_size", "medium").apply()
            recreate()

            Toast.makeText(this, "Settings reset", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLocale(languageCode: String) {
        sharedPreferences.edit().putString("LANGUAGE", languageCode).apply()

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // restart for locale change
        val intent = Intent(this, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun setSettingsText() {
        settingsBinding.toggleAccessibility.text = getString(R.string.toggleAccessibility)
        settingsBinding.toggleFont.text = getString(R.string.toggleFont)
        settingsBinding.toggleLanguage.text = getString(R.string.toggleLanguage)
        settingsBinding.btnResetSettings.text = getString(R.string.btnResetSettings)
        settingsBinding.tvToggleAudio.text = getString(R.string.tvToggleAudio)
        settingsBinding.tvToggleNotifications.text =
            getString(R.string.tvToggleNotifications)
    }
}
