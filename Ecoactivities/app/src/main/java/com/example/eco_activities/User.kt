package com.example.eco_activities

data class User(
    val login: String,
    val email: String,
    val pass: String,
    val role: String = "user" // "user" или "organizer"
)