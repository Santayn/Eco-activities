package com.example.eco_activities

data class Item(
    val id: Int,
    val image: String,
    val title: String,
    val desc: String,
    val text: String,
    val price: Int,
    val date: String,
    val status: String,
    val organizerId: Int,
    val signUpCount: Int = 0    // ← новое поле, по умолчанию 0
)