package com.example.chestermilitarymuseum

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chestermilitarymuseum.adapters.NewsAdapter
import com.example.chestermilitarymuseum.databinding.ActivityBaseBinding
import com.example.chestermilitarymuseum.databinding.ActivityNewsBinding
import com.example.chestermilitarymuseum.network.RetrofitClient
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private lateinit var baseBinding: ActivityBaseBinding
    private lateinit var newsBinding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate base layout (with header + footer)
        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(baseBinding.root)

        // Inflate the news content inside the container
        val inflater = LayoutInflater.from(this)
        newsBinding = ActivityNewsBinding.inflate(inflater)
        baseBinding.container.removeAllViews()
        baseBinding.container.addView(newsBinding.root)

        // Setup RecyclerView
        newsBinding.newsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Load news data from API
        lifecycleScope.launch {
            try {
                val posts = RetrofitClient.api.getPosts()
                newsBinding.newsRecyclerView.adapter = NewsAdapter(posts)
            } catch (e: Exception) {
                Toast.makeText(this@NewsActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }

        // Highlight News icon
        baseBinding.bottomNavigation.selectedItemId = R.id.navigation_news

        // Setup navigation
        baseBinding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish()
                    true
                }
                R.id.navigation_news -> true
                else -> false
            }
        }
    }
}
