package com.example.eco.api.dto.registration

data class UserRegistrationResponseDto(
    val id: Int,
    val fullName: String,
    val login: String,
    val role: String
)