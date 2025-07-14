package com.example.eco.api.dto.event

data class Event(
    val id: Int,
    val title: String,
    val status: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val imageResId: Int? = null // ID изображения (для Jetpack Compose)
)