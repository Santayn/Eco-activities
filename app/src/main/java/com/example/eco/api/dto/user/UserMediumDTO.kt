package com.example.eco.api.dto.user

data class UserMediumDTO(
    val id: Int,
    val fullName: String,
    val registeredEventsCount: Int,
    val totalBonusPoints: Int,
    val phoneNumber: String? = null,
    val email: String? = null
)