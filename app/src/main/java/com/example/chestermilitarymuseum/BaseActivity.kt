package com.example.chestermilitarymuseum

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

//Code adapted from Panchal (January 2025) [Medium].
open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
        val currentLanguage = sharedPreferences.getString("LANGUAGE", "en") ?: "en"

        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}