package com.example.eco.api

data class LoginResponse(
    val id: Int,
    val fullName: String,
    val login: String,
    val role: String,
    val token: String
)