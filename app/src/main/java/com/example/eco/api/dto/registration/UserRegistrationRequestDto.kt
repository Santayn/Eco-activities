package com.example.eco.api.dto.registration

data class UserRegistrationRequestDto(
    val fullName: String,
    val login: String,
    val password: String,
    val role: String,
    val phoneNumber: String? = null,
    val email: String? = null
)