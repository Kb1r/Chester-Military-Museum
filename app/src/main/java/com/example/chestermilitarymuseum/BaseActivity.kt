package com.example.chestermilitarymuseum

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap.Config
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

//Code adapted from Panchal (January 2025) [Medium].
open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
        val currentLanguage = sharedPreferences.getString("LANGUAGE", "en") ?: "en"
        val choice = sharedPreferences.getString("font_size", "medium") ?: "medium"

        val scale = when (choice)
        {
            "small" -> 0.85f
            "large" -> 1.4f
            else -> 1.0f
        }

        val locale = Locale(currentLanguage)

        val config = Configuration(newBase.resources.configuration)
            .apply {fontScale = scale}
        config.setLocale(locale)


        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}