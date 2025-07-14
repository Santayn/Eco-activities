package com.example.eco.api.dto.participants

import com.example.eco.api.dto.bonus.UserShortDTO

data class EventParticipantDTO(
    val status: String, // CONFIRMED, PENDING, CANCELLED
    val createdAt: String, // ISO 8601 date-time
    val membershipStatus: String, // VALID, INVALID
    val user: UserShortDTO,
    val event: EventShortDTO
)