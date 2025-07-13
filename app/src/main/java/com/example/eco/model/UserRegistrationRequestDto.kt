package com.example.eco.model

data class UserRegistrationRequestDto(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)