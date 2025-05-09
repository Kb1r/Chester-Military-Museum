package com.example.chestermilitarymuseum

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chestermilitarymuseum.adapters.NewsAdapter
import com.example.chestermilitarymuseum.databinding.ActivityNewsBinding
import com.example.chestermilitarymuseum.network.RetrofitClient
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            try {
                val posts = RetrofitClient.api.getPosts()
                binding.newsRecyclerView.adapter = NewsAdapter(posts)
            } catch (e: Exception) {
                Toast.makeText(this@NewsActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
