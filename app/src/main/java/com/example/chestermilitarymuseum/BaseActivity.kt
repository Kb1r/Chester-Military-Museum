package com.example.chestermilitarymuseum

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        // 1) Read saved font-size choice
        val prefs = newBase.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val choice = prefs.getString("font_size", "medium") ?: "medium"

        // 2) Map choice to a float scale
        val scale = when (choice) {
            "small"  -> 0.85f
            "large"  -> 1.4f
            else     -> 1.0f    // “medium” or default
        }

        // 3) Create a configuration with that fontScale
        val config = Configuration(newBase.resources.configuration).apply {
            fontScale = scale
        }

        // 4) Wrap the base context with the new config
        val ctx = newBase.createConfigurationContext(config)
        super.attachBaseContext(ctx)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Subclasses still call setContentView(...) as usual
    }
}
