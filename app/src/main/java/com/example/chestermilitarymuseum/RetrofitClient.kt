package com.example.chestermilitarymuseum.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: WordpressApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://cheshiremilitarymuseum.org.uk/wp-json/wp/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WordpressApi::class.java)
    }
}
