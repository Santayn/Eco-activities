package com.example.eco.api.dto.event

data class OwnerDTO(
    val id: Int,
    val fullName: String,
    val registeredEventsCount: Int,
    val totalBonusPoints: Int,
    val phoneNumber: String,
    val email: String
)