package com.example.eco.api.dto.registration

data class RegisterOrUnregisterRequest(
    val userId: Int,
    val eventId: Int
)