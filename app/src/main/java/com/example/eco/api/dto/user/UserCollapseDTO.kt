package com.example.eco.api.dto.user

data class UserCollapseDTO(
    val id: Int, // integer (int32)
    val fullName: String,
    val login: String,
    val password: String,
    val role: String,
    val registeredEventsCount: Int, // integer (int32)
    val totalBonusPoints: Int, // integer (int32)
    val phoneNumber: String? = null, // Необязательное поле
    val email: String? = null // Необязательное поле
)