package com.example.eco.api.dto.auth

data class AuthResponseDto(
    val id: Int,
    val fullName: String,
    val login: String,
    val role: String,
    val token: String
)