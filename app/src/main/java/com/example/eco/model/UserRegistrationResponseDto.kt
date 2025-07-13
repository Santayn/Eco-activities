package com.example.eco.model

data class UserRegistrationResponseDto(
    val id: Int,
    val username: String,
    val email: String,
    val role: String
)