package com.example.eco_activities

data class Item(
    val id: Int,
    val image: String, // Имя файла изображения (например, "first")
    val title: String,
    val desc: String,
    val text: String,
    val price: Int,
    val date: String,
    val status: String,
    val organizerId: Int
)