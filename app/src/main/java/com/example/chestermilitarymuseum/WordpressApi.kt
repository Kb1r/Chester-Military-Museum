package com.example.chestermilitarymuseum.network

import com.example.chestermilitarymuseum.models.Post
import retrofit2.http.GET

interface WordpressApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
