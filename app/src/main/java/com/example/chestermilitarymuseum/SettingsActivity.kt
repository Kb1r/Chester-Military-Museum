package com.example.chestermilitarymuseum

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.SettingsLayoutBinding
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var baseBinding: ActivityBaseBinding
    private lateinit var settingsBinding: SettingsLayoutBinding
    private lateinit var sharedPreferences: SharedPreferences

    //Creating the map that represents the language selected using ISO 639-1 code.
    private val languages = mapOf(
        "English" to "en",
        "French" to "fr",
        "Spanish" to "es",
        "German" to "de"
    )

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

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        val currentLanguage = sharedPreferences.getString("LANGUAGE", "en")
        settingsBinding.spinnerLanguage.setSelection(languages.values.indexOf(currentLanguage))

        //Test approach
        settingsBinding.spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = languages.values.toList()[position]
                if (selectedLanguage != currentLanguage) {
                    setLocale(selectedLanguage)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            //End of test approach
        }


        // Handle navigation
        baseBinding.bottomNavigation.selectedItemId = R.id.navigation_settings
        baseBinding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity((Intent(this, MainActivity::class.java)))
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
            settingsBinding.spinnerLanguage.visibility =
                if (settingsBinding.spinnerLanguage.visibility == View.GONE) View.VISIBLE else View.GONE
        }
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    //Now uses the keys from the map.
    private fun setupLanguageDropdown() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

    //Setting the locale
    fun setLocale(languageCode: String) {
        val languageEditor = sharedPreferences.edit()
        languageEditor.putString("LANGUAGE", languageCode)
        languageEditor.apply()

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        val intent = Intent(this, SettingsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()

    }
}
