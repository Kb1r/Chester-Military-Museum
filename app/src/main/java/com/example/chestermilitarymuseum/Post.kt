package com.example.chestermilitarymuseum.models

data class Post(
    val id: Int,
    val title: Rendered,
    val excerpt: Rendered,
    val link: String,
    val jetpack_featured_media_url: String
)

data class Rendered(val rendered: String)
